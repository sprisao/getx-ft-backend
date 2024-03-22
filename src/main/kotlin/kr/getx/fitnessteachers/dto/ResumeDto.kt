package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.*
import java.time.LocalDate
import java.time.LocalDateTime

data class ResumeDto(
        var resumeId : Int,
        var userId: Int,
        val photos: List<String>,
        val experiences: List<ExperienceDto>,
        val educations: List<EducationDto>,
        val certifications: List<CertificationDto>,
        var appliedJobPostIds: MutableList<Int>? = null,
        val createdAt : LocalDateTime?
)
{
        fun toResume(user: User, photosString: String): Resume = Resume(
                user = user,
                photos = photosString,
                createdAt = this.createdAt ?: LocalDateTime.now()
        )
}

data class ExperienceDto(
        val experienceId: Int,
        val description: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val createdAt: LocalDateTime?
)
{
        fun toExperience(resume: Resume): Experience = Experience(
                experienceId = this.experienceId,
                resume = resume,
                description = this.description,
                startDate = this.startDate,
                endDate = this.endDate,
                createdAt = this.createdAt ?: LocalDateTime.now()
        )
}

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
}

data class CertificationDto(
        val certificationId: Int,
        val name: String,
        val issuedBy: String,
        val issuedDate: LocalDate,
        val createdAt: LocalDateTime?
)
{
        fun toCertification(resume: Resume): Certification = Certification(
                certificationId = this.certificationId,
                resume = resume,
                name = this.name,
                issuedBy = this.issuedBy,
                issuedDate = this.issuedDate,
                createdAt = this.createdAt ?: LocalDateTime.now()
        )
}