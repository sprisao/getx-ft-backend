package kr.getx.fitnessteachers.dto

import com.fasterxml.jackson.annotation.JsonFormat
import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.entity.JobPost
import java.time.LocalDate
import java.time.LocalDateTime

data class JobPostDto(
    val jobPostId: Int = 0,
    val center: CenterDto,
    val isPostCompleted: Boolean? = false,
    val isRecruitmentOpen: Boolean? = false,
    val jobCategories: String?,
    val workLocation: String?,
    val workDays: List<WorkDayDto> = emptyList(),
    val employmentType: String?,
    val salaryType: String?,
    val additionalSalary: String?,
    val minSalary: Int? = null,
    val maxSalary: Int? = null,
    val experienceLevel: Int?,
    val numberOfPositions: Int?,
    val qualifications: String?,
    val preferences: String?,
    val details: String?,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val applicationPeriodEnd: LocalDate? = null,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val workStartDate: LocalDate?,
    val isDeleted: Boolean = false,
    val isDeletedAt: LocalDateTime? = null,
    val title: String?,
    val link: String?,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val postedDate: LocalDateTime? = LocalDateTime.now()
) {
    fun toEntity(center: Center): JobPost {
        val jobPost = JobPost(
            jobPostId = jobPostId,
            center = center,
            isPostCompleted = isPostCompleted ?: false,
            isRecruitmentOpen = isRecruitmentOpen ?: false,
            jobCategories = jobCategories ?: "",
            workLocation = workLocation ?: "",
            employmentType = employmentType ?: "",
            salaryType = salaryType ?: "",
            additionalSalary = additionalSalary ?: "",
            minSalary = minSalary ?: 0,
            maxSalary = maxSalary ?: 0,
            experienceLevel = experienceLevel ?: 0,
            numberOfPositions = numberOfPositions ?: 0,
            qualifications = qualifications ?: "",
            preferences = preferences ?: "",
            details = details ?: "",
            applicationPeriodEnd = applicationPeriodEnd,
            workStartDate = workStartDate,
            isDeleted = isDeleted,
            isDeletedAt = isDeletedAt,
            title = title ?: "",
            link = link ?: "",
            createdAt = createdAt ?: LocalDateTime.now(),
            postedDate = postedDate ?: LocalDateTime.now()
        )

        jobPost.workDays = workDays.map { it.toEntity(jobPost) }

        return jobPost
    }

    companion object {
        fun fromEntity(jobPost: JobPost): JobPostDto = JobPostDto(
            jobPostId = jobPost.jobPostId,
            center = CenterDto.fromEntity(jobPost.center),
            isPostCompleted = jobPost.isPostCompleted,
            isRecruitmentOpen = jobPost.isRecruitmentOpen,
            jobCategories = jobPost.jobCategories,
            workLocation = jobPost.workLocation,
            workDays = jobPost.workDays.map { WorkDayDto.fromEntity(it) },
            employmentType = jobPost.employmentType,
            salaryType = jobPost.salaryType,
            additionalSalary = jobPost.additionalSalary,
            minSalary = jobPost.minSalary,
            maxSalary = jobPost.maxSalary,
            experienceLevel = jobPost.experienceLevel,
            numberOfPositions = jobPost.numberOfPositions,
            qualifications = jobPost.qualifications,
            preferences = jobPost.preferences,
            details = jobPost.details,
            applicationPeriodEnd = jobPost.applicationPeriodEnd,
            workStartDate = jobPost.workStartDate,
            isDeleted = jobPost.isDeleted,
            isDeletedAt = jobPost.isDeletedAt,
            title = jobPost.title,
            link = jobPost.link,
            createdAt = jobPost.createdAt,
            postedDate = jobPost.postedDate
        )
    }
}