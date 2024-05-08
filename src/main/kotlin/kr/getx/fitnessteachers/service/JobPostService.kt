package kr.getx.fitnessteachers.service

import jakarta.transaction.Transactional
import kr.getx.fitnessteachers.dto.JobPostDto
import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.entity.WorkDay
import kr.getx.fitnessteachers.repository.JobPostRepository
import org.springframework.stereotype.Service
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import kr.getx.fitnessteachers.exceptions.JobPostNotFoundException
import java.util.Base64
import kotlin.math.ln
import kotlin.math.sqrt

@Service
class JobPostService(
    private val jobPostRepository: JobPostRepository,
    private val centerService: CenterService,
    private val workDayService: WorkDayService,
) {
    fun findAll(): List<JobPost> = jobPostRepository.findAll()

    fun findById(id: Int): JobPost? = jobPostRepository.findById(id).orElse(null)

    fun createJobPost(jobPostDto: JobPostDto, center: Center): JobPost {
        val jobPost = jobPostDto.toEntity(center)
        val savedJobPost = jobPostRepository.save(jobPost)

        val workDays = jobPostDto.workDays.map { workDayDto ->
            workDayDto.jobPostId = savedJobPost.jobPostId
            workDayDto.toEntity(savedJobPost)
        }
        savedJobPost.workDays = workDays

        return savedJobPost
    }

    fun deleteById(jobPostId: Int) {
        val jobPost = jobPostRepository.findByJobPostIdAndDeletedFalse(jobPostId).orElseThrow {
            JobPostNotFoundException(jobPostId)
        }
        jobPost.isDeleted = true
        jobPostRepository.save(jobPost)
    }

    @Transactional
    fun updateJobPost(jobPostId: Int, jobPostDto: JobPostDto, user: User): JobPost {
        val jobPost = findById(jobPostId) ?: throw JobPostNotFoundException(jobPostId)

        workDayService.deleteByJobPostJobPostId(jobPostId)

        // DTO의 데이터로 JobPost 엔티티 업데이트
        jobPost.apply {
            isPostCompleted = jobPostDto.isPostCompleted
            isRecruitmentOpen = jobPostDto.isRecruitmentOpen
            jobCategories = jobPostDto.jobCategories
            workLocation = jobPostDto.workLocation
            workDays = jobPostDto.workDays.map { workDayDto ->
                WorkDay(
                    jobPost = this,
                    day = workDayDto.day,
                    startTime = workDayDto.startTime,
                    endTime = workDayDto.endTime
                )
            }
            employmentType = jobPostDto.employmentType
            salaryType = jobPostDto.salaryType
            additionalSalary = jobPostDto.additionalSalary
            minSalary = jobPostDto.minSalary
            maxSalary = jobPostDto.maxSalary
            experienceLevel = jobPostDto.experienceLevel
            numberOfPositions = jobPostDto.numberOfPositions
            qualifications = jobPostDto.qualifications ?: ""
            preferences = jobPostDto.preferences ?: ""
            details = jobPostDto.details ?: ""
            applicationPeriodEnd = jobPostDto.applicationPeriodEnd
            workStartDate = jobPostDto.workStartDate
        }


        return jobPostRepository.save(jobPost)
    }

    fun findJobPostsByUserId(userId: Int): List<JobPost> =
        centerService.getCenterByUserId(userId)
            .flatMap { jobPostRepository.findByCenterCenterId(centerId = it.centerId) }

    // 검색 기능 추가
    fun searchJobPosts(
        jobCategories: String?,
        employmentType: String?,
        salaryType: String?,
        experienceLevel: Int?,
        sidoEnglish: String?,
        sigunguEnglish: String?,
        day: String?,
        startTime: String?,
        endTime: String?,
        pageable: Pageable
    ): Page<JobPost> {
        return jobPostRepository.search(
            jobCategories = jobCategories,
            employmentType = employmentType,
            salaryType = salaryType,
            experienceLevel = experienceLevel,
            sidoEnglish = sidoEnglish,
            sigunguEnglish = sigunguEnglish,
            day = day,
            startTime = startTime,
            endTime = endTime,
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
            .take(5) // 5개만 가져오기
            .map { JobPostDto.fromEntity(it.first) }
            .toList()
    }

    private fun cosineSimilarity(vec1: Map<String, Double>, vec2: Map<String, Double>): Double {
        val intersection = vec1.keys.intersect(vec2.keys)
        val numerator = intersection.sumOf { vec1[it]!! * vec2[it]!! }
        val denominator = sqrt(vec1.values.sumOf { it * it }) * sqrt(vec2.values.sumOf { it * it })
        return if (denominator > 0) numerator / denominator else 0.0
    }

    // 단축 공유 URL 생성 API Service 구현
    fun generateShortUrl(jobPost: JobPost): String {
        val baseUrl = "http://52.79.241.48"
        val encodedId = Base64.getUrlEncoder().encodeToString(jobPost.jobPostId.toString().toByteArray())
        return "$baseUrl/s/$encodedId"
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
