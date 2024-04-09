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
    val workHours: String,
    val workDays: String,
    val employmentType: String,
    val hasBaseSalary: Boolean,
    val salaryType: String,
    val salary: String,
    val experienceLevel: Int,
    val isSecondLanguageAvailable: Boolean = false,
    val isMajorDegreeRequired: Boolean = false,
    val numberOfPositions: Int,
    val qualifications: String,
    val preferences: String,
    val details: String,
    val applicationPeriodEnd: LocalDate? = null,
    val workStartDate: LocalDate?,
    val postedDate: LocalDateTime = LocalDateTime.now()
) {
    fun toEntity(center: Center): JobPost = JobPost(
        jobPostId = jobPostId,
        center = center,
        isPostCompleted = isPostCompleted,
        isRecruitmentOpen = isRecruitmentOpen,
        jobCategories = jobCategories,
        workLocation = workLocation,
        workHours = workHours,
        workDays = workDays,
        employmentType = employmentType,
        hasBaseSalary = hasBaseSalary,
        salaryType = salaryType,
        salary = salary,
        experienceLevel = experienceLevel,
        isSecondLanguageAvailable = isSecondLanguageAvailable,
        isMajorDegreeRequired = isMajorDegreeRequired,
        numberOfPositions = numberOfPositions,
        qualifications = qualifications,
        preferences = preferences,
        details = details,
        applicationPeriodEnd = applicationPeriodEnd,
        workStartDate = workStartDate,
        postedDate = postedDate
    )
    companion object {
        fun fromEntity(jobPost: JobPost): JobPostDto = JobPostDto(
            jobPostId = jobPost.jobPostId,
            center = CenterDto.fromEntity(jobPost.center),
            isPostCompleted = jobPost.isPostCompleted,
            isRecruitmentOpen = jobPost.isRecruitmentOpen,
            jobCategories = jobPost.jobCategories,
            workLocation = jobPost.workLocation,
            workHours = jobPost.workHours,
            workDays = jobPost.workDays,
            employmentType = jobPost.employmentType,
            hasBaseSalary = jobPost.hasBaseSalary,
            salaryType = jobPost.salaryType,
            salary = jobPost.salary,
            experienceLevel = jobPost.experienceLevel,
            isSecondLanguageAvailable = jobPost.isSecondLanguageAvailable,
            isMajorDegreeRequired = jobPost.isMajorDegreeRequired,
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
