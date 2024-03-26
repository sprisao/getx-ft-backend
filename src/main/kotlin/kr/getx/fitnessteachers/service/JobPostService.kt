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
    fun findSimilarJobPosts(jobPostId: Int): List<JobPost> {
        val originalJobPost = findById(jobPostId) ?: throw JobPostNotFoundException(jobPostId)

        return findAll().filter { jobPost ->
            jobPost.title?.split(" ")?.any { word ->
                originalJobPost.title?.contains(word, ignoreCase = true) ?: false
            } == true && jobPost.jobPostId != originalJobPost.jobPostId
        }
    }
}
