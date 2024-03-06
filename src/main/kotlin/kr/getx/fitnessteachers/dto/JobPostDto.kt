package kr.getx.fitnessteachers.dto

import java.time.LocalDateTime

data class JobPostDto(
    val jobPostId: Int,
    val centerId: Int,
    val recruitmentStatus: String? = null,
    val responsibilities: String? = null,
    val workLocation: String? = null,
    val workHours: String? = null,
    val workDays: String? = null,
    val employmentType: String? = null,
    val numberOfPositions: Int? = null,
    val salary: String? = null,
    val qualifications: String? = null,
    val applicationPeriodStart: LocalDateTime? = null,
    val applicationPeriodEnd: LocalDateTime? = null,
    val contactEmail: String? = null,
    val contactPhone: String? = null,
    val contactPerson: String? = null,
    val details: String? = null,
)