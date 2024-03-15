package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.JobPostDto
import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.repository.JobPostRepository
import org.springframework.stereotype.Service
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import kr.getx.fitnessteachers.exceptions.JobPostNotFoundException

@Service
class JobPostService(private val jobPostRepository: JobPostRepository) {

    fun findAll(): List<JobPost> = jobPostRepository.findAll()

    fun findById(id: Int): JobPost? = jobPostRepository.findById(id).orElse(null)

    fun save(jobPost: JobPost): JobPost = jobPostRepository.save(jobPost)

    fun deleteById(id: Int) = jobPostRepository.deleteById(id)

    fun updateJobPost(existingJobPost: JobPost, jobPostDto: JobPostDto): JobPost {
        // JobPostDto의 정보로 기존 JobPost 엔티티 업데이트
        existingJobPost.apply {
            recruitmentStatus = jobPostDto.recruitmentStatus
            responsibilities = jobPostDto.responsibilities
            workLocation = jobPostDto.workLocation
            workHours = jobPostDto.workHours
            workDays = jobPostDto.workDays
            employmentType = jobPostDto.employmentType
            numberOfPositions = jobPostDto.numberOfPositions
            salary = jobPostDto.salary
            qualifications = jobPostDto.qualifications
            applicationPeriodStart = jobPostDto.applicationPeriodStart
            applicationPeriodEnd = jobPostDto.applicationPeriodEnd
            contactEmail = jobPostDto.contactEmail
            contactPhone = jobPostDto.contactPhone
            contactPerson = jobPostDto.contactPerson
            details = jobPostDto.details
        }

        return jobPostRepository.save(existingJobPost)
    }

    fun findByCenterId(centerId: Int): List<JobPost> = jobPostRepository.findByCenter_CenterId(centerId)

    // 검색 기능 추가
    fun searchJobPosts(
        recruitmentStatus: String?,
        jobCategory: String?,
        locationProvince: String?,
        locationCity: String?,
        pageable: Pageable
    ) : Page<JobPost> {
        return jobPostRepository.findByRecruitmentStatusAndJobCategoryAndCenterLocationProvinceAndCenterLocationCity(
            recruitmentStatus,
            jobCategory,
            locationProvince,
            locationCity,
            pageable
        )
    }

    // 지원자 ID 추가
    fun applyToJobPost(jobPostId: Int, userId: Int) : String{
        val jobPost = findById(jobPostId)
            ?: throw JobPostNotFoundException(jobPostId)

        if (jobPost.applicationUserIds.contains(userId)) {
            throw IllegalArgumentException("이미 지원한 구인게시판입니다.")
        }

        jobPost.applicationUserIds.add(userId)
        save(jobPost)
        return "구인게시판에 지원하였습니다."
    }

    // 지원 취소
    fun cancelApplication(jobPostId: Int, userId: Int) : String {
        val jobPost = findById(jobPostId)
            ?: throw JobPostNotFoundException(jobPostId)

        if (!jobPost.applicationUserIds.contains(userId)) {
            throw IllegalArgumentException("지원하지 않은 구인게시판입니다.")
        }

        jobPost.applicationUserIds.remove(userId)
        save(jobPost)
        return "구인게시판 지원을 취소하였습니다."
    }
}
