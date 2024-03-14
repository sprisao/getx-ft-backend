package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.JobPostDto
import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.repository.JobPostRepository
import org.springframework.stereotype.Service
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

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
        return jobPostRepository.findByCriteria(
            recruitmentStatus,
            jobCategory,
            locationProvince,
            locationCity,
            pageable
        )
    }
}
