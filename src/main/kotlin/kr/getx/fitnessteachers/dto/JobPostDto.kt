package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.entity.JobPost
import java.time.LocalDate

data class JobPostDto(
    val jobPostId: Int? = null,
    val centerId: Int,
    val isDisplayAlready: Boolean? = false,
    val recruitmentStatus: Boolean? = false,
    val responsibilities: String? = null,
    val workLocation: String? = null,
    val workHours: String? = null,
    val workDays: String? = null,
    val employmentType: String? = null,
    val numberOfPositions: Int? = null,
    val salary: String? = null,
    val qualifications: String? = null,
    val applicationPeriodStart: LocalDate? = null,
    val applicationPeriodEnd: LocalDate? = null,
    val contactEmail: String? = null,
    val contactPhone: String? = null,
    val contactPerson: String? = null,
    val title: String? = null,
    val details: String? = null,
    val jobCategory: String? = null,
) {
    fun toEntity(center: Center): JobPost = JobPost(
        jobPostId = jobPostId ?: 0,
        center = center,
        isDisplayAlready = isDisplayAlready ?: false,
        recruitmentStatus = recruitmentStatus ?: false,
        responsibilities = responsibilities,
        workLocation = workLocation,
        workHours = workHours,
        workDays = workDays,
        employmentType = employmentType,
        numberOfPositions = numberOfPositions ?: 0,
        salary = salary,
        qualifications = qualifications,
        applicationPeriodStart = applicationPeriodStart,
        applicationPeriodEnd = applicationPeriodEnd,
        contactEmail = contactEmail,
        contactPhone = contactPhone,
        contactPerson = contactPerson,
        title = title,
        details = details,
        jobCategory = jobCategory
    )
    companion object {
        fun fromEntity(jobPost: JobPost): JobPostDto = JobPostDto(
            jobPostId = jobPost.jobPostId,
            centerId = jobPost.center.centerId,
            isDisplayAlready = jobPost.isDisplayAlready,
            recruitmentStatus = jobPost.recruitmentStatus,
            responsibilities = jobPost.responsibilities,
            workLocation = jobPost.workLocation,
            workHours = jobPost.workHours,
            workDays = jobPost.workDays,
            employmentType = jobPost.employmentType,
            numberOfPositions = jobPost.numberOfPositions,
            salary = jobPost.salary,
            qualifications = jobPost.qualifications,
            applicationPeriodStart = jobPost.applicationPeriodStart,
            applicationPeriodEnd = jobPost.applicationPeriodEnd,
            contactEmail = jobPost.contactEmail,
            contactPhone = jobPost.contactPhone,
            contactPerson = jobPost.contactPerson,
            title = jobPost.title,
            details = jobPost.details,
            jobCategory = jobPost.jobCategory
        )
    }
}
