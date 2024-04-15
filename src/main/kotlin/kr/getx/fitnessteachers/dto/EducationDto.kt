package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.Education
import java.time.LocalDate
import java.time.LocalDateTime

data class EducationDto(
    val educationId: Int? = null,
    val userId: Int,
    val courseName: String,
    val institution: String,
    val completionDate: LocalDate,
    val createdAt: LocalDateTime? = null
)
{
    companion object {
        fun fromEntity(education: Education): EducationDto = EducationDto(
            educationId = education.educationId,
            userId = education.user.userId,
            courseName = education.courseName,
            institution = education.institution,
            completionDate = education.completionDate,
            createdAt = education.createdAt
        )
    }
}