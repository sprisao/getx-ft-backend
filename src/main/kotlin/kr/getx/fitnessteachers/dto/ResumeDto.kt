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
        val createdAt : LocalDateTime?
)
{
        fun toResume(user: User, photosString: String): Resume = Resume(
                user = user,
                photos = photosString,
                createdAt = this.createdAt ?: LocalDateTime.now()
        )
}
