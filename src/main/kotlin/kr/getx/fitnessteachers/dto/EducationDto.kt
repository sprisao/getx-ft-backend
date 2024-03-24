package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.Education
import kr.getx.fitnessteachers.entity.Resume
import java.time.LocalDate
import java.time.LocalDateTime

data class EducationDto(
    val educationId: Int,
    val courseName: String,
    val institution: String,
    val completionDate: LocalDate,
    val createdAt: LocalDateTime?
)
{
    fun toEducation(resume: Resume): Education = Education(
        educationId = this.educationId,
        resume = resume,
        courseName = this.courseName,
        institution = this.institution,
        completionDate = this.completionDate,
        createdAt = this.createdAt ?: LocalDateTime.now()
    )
    companion object {
        fun fromEntity(education: Education): EducationDto = EducationDto(
            educationId = education.educationId,
            courseName = education.courseName,
            institution = education.institution,
            completionDate = education.completionDate,
            createdAt = education.createdAt
        )
    }
}