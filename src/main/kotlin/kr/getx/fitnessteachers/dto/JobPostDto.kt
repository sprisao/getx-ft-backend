package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.entity.JobPost
import java.time.LocalDate
import java.time.LocalDateTime

data class JobPostDto(
    val jobPostId: Int = 0,
    val center: CenterDto,
    val isPostCompleted: Boolean = false,
    val isRecruitmentOpen: Boolean = false,
    val jobCategories: List<String>,
    val workLocation: String,
    val workDays: List<WorkDayDto>,
    val employmentType: String,
    val salaryType: String,
    val additionalSalary: String,
    val minSalary: Int,
    val maxSalary: Int,
    val experienceLevel: Int,
    val numberOfPositions: Int,
    val qualifications: String?,
    val preferences: String?,
    val details: String?,
    val applicationPeriodEnd: LocalDate? = null,
    val workStartDate: LocalDate?,
    val postedDate: LocalDateTime = LocalDateTime.now()
) {
    fun toEntity(center: Center): JobPost {
        val jobPost = JobPost(
            jobPostId = jobPostId,
            center = center,
            isPostCompleted = isPostCompleted,
            isRecruitmentOpen = isRecruitmentOpen,
            jobCategories = jobCategories,
            workLocation = workLocation,
            employmentType = employmentType,
            salaryType = salaryType,
            additionalSalary = additionalSalary,
            minSalary = minSalary,
            maxSalary = maxSalary,
            experienceLevel = experienceLevel,
            numberOfPositions = numberOfPositions,
            qualifications = qualifications ?: "",
            preferences = preferences ?: "",
            details = details ?: "",
            applicationPeriodEnd = applicationPeriodEnd,
            workStartDate = workStartDate,
            postedDate = postedDate
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
            workLocation = jobPost.workLocation ?: "",
            workDays = jobPost.workDays.map{ WorkDayDto.fromEntity(it)},
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
            postedDate = jobPost.postedDate
        )
    }
}
