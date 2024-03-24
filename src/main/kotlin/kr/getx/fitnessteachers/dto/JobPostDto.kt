package kr.getx.fitnessteachers.dto

import java.time.LocalDate

data class JobPostDto(
    val jobPostId: Int,
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
)
