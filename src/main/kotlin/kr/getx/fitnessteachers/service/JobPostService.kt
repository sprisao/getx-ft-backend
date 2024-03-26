package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.JobPostDto
import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.repository.JobPostRepository
import org.springframework.stereotype.Service
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import kr.getx.fitnessteachers.exceptions.JobPostNotFoundException
import kotlin.math.ln
import kotlin.math.sqrt

@Service
class JobPostService(
    private val jobPostRepository: JobPostRepository,
    private val centerService: CenterService,
) {

    fun findAll(): List<JobPost> = jobPostRepository.findAll()

    fun findById(id: Int): JobPost? = jobPostRepository.findById(id).orElse(null)

    fun createJobPost(jobPostDto: JobPostDto, center:Center, user: User): JobPost {
        val jobPost = jobPostDto.toEntity(center)
        return jobPostRepository.save(jobPost)
    }

    fun deleteById(id: Int) = jobPostRepository.deleteById(id)

    fun updateJobPost(jobPostId: Int, jobPostDto: JobPostDto, user:User): JobPost {
        val jobPost = findById(jobPostId) ?: throw JobPostNotFoundException(jobPostId)

        // DTO의 데이터로 JobPost 엔티티 업데이트
        jobPost.apply {
            isDisplayReady = jobPostDto.isDisplayReady ?: isDisplayReady
            recruitmentStatus = jobPostDto.recruitmentStatus ?: recruitmentStatus
            responsibilities = jobPostDto.responsibilities ?: responsibilities
            workLocation = jobPostDto.workLocation ?: workLocation
            workHours = jobPostDto.workHours ?: workHours
            workDays = jobPostDto.workDays ?: workDays
            employmentType = jobPostDto.employmentType ?: employmentType
            numberOfPositions = jobPostDto.numberOfPositions ?: numberOfPositions
            salary = jobPostDto.salary ?: salary
            qualifications = jobPostDto.qualifications ?: qualifications
            applicationPeriodStart = jobPostDto.applicationPeriodStart ?: applicationPeriodStart
            applicationPeriodEnd = jobPostDto.applicationPeriodEnd ?: applicationPeriodEnd
            contactEmail = jobPostDto.contactEmail ?: contactEmail
            contactPhone = jobPostDto.contactPhone ?: contactPhone
            contactPerson = jobPostDto.contactPerson ?: contactPerson
            title = jobPostDto.title ?: title
            details = jobPostDto.details ?: details
            jobCategory = jobPostDto.jobCategory ?: jobCategory
        }
        return jobPostRepository.save(jobPost)
    }

    fun findJobPostsByUserId(userId: Int): List<JobPost> =
        centerService.getCenterByUserId(userId).flatMap { jobPostRepository.findByCenterCenterId(centerId = it.centerId) }

    // 검색 기능 추가
    fun searchJobPosts(
        recruitmentStatus: String?,
        jobCategory: String?,
        locationProvince: String?,
        locationCity: String?,
        pageable: Pageable
    ): Page<JobPost> {
        return jobPostRepository.search(
            recruitmentStatus = recruitmentStatus,
            jobCategory = jobCategory,
            locationProvince = locationProvince,
            locationCity = locationCity,
            pageable = pageable
        )
    }

    // 유사 게시글 검색
    fun findSimilarJobPosts(jobPostId: Int): List<JobPostDto> {
        val targetJobPost = jobPostRepository.findById(jobPostId).orElseThrow {
            IllegalArgumentException("JobPost not found: $jobPostId")
        }

        val jobPosts = jobPostRepository.findAll()
        val documents = jobPosts.map { it.details ?: "" }
        val tfidfVectorizer = TfidfVectorizer()
        val tfidfMatrix = tfidfVectorizer.fitTransform(documents)

        val targetIndex = jobPosts.indexOfFirst { it.jobPostId == jobPostId }
        val targetVector = tfidfMatrix[targetIndex]

        return jobPosts.asSequence()
            .mapIndexedNotNull { index, jobPost ->
                if (index == targetIndex) null
                else jobPost to cosineSimilarity(tfidfMatrix[index], targetVector)
            }
            .filter { it.second > 0.5 }
            .sortedByDescending { it.second }
            .take(5)
            .map { JobPostDto.fromEntity(it.first) }
            .toList()
    }

    private fun cosineSimilarity(vec1: Map<String, Double>, vec2: Map<String, Double>): Double {
        val intersection = vec1.keys.intersect(vec2.keys)
        val numerator = intersection.sumOf { vec1[it]!! * vec2[it]!! }
        val denominator = sqrt(vec1.values.sumOf { it * it }) * sqrt(vec2.values.sumOf { it * it })
        return if (denominator > 0) numerator / denominator else 0.0
    }
}

class TfidfVectorizer {
    private lateinit var idfValues: Map<String, Double>

    fun fitTransform(documents: List<String>): List<Map<String, Double>> {
        val docFreq = documents.flatMap { it.split("\\s+").toSet() }.groupingBy { it }.eachCount()
        val totalDocs = documents.size.toDouble()

        idfValues = docFreq.mapValues { (_, count) -> ln(totalDocs / (1 + count)) }

        return documents.map { doc -> tfidf(doc) }
    }

    private fun tfidf(document: String): Map<String, Double> {
        val termFreq = document.split("\\s+").groupingBy { it }.eachCount()
        val docSize = termFreq.values.sum().toDouble()

        return termFreq.mapValues { (term, count) -> (count / docSize) * idfValues.getValue(term) }
    }
}
