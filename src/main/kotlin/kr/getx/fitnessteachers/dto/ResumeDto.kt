package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.*
import java.time.LocalDate

data class ResumeDto(
        val userId: Int,
        val photos: List<String>,
        val experiences: List<ExperienceDto>,
        val educations: List<EducationDto>,
        val certifications: List<CertificationDto>
)
{
        fun toResume(user: User, photosString: String): Resume = Resume(
                user = user,
                photos = photosString
        )
}

data class ExperienceDto(
        val description: String,
        val startDate: LocalDate,
        val endDate: LocalDate
)
{
        fun toExperience(resume: Resume): Experience = Experience(
                resume = resume,
                description = this.description,
                startDate = this.startDate,
                endDate = this.endDate
        )
}

data class EducationDto(
        val courseName: String,
        val institution: String,
        val completionDate: LocalDate
)
{
        fun toEducation(resume: Resume): Education = Education(
                resume = resume,
                courseName = this.courseName,
                institution = this.institution,
                completionDate = this.completionDate
        )
}

data class CertificationDto(
        val name: String,
        val issuedBy: String,
        val issuedDate: LocalDate
)
{
        fun toCertification(resume: Resume): Certification = Certification(
                resume = resume,
                name = this.name,
                issuedBy = this.issuedBy,
                issuedDate = this.issuedDate
        )
}