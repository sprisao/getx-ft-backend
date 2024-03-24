package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.Experience
import kr.getx.fitnessteachers.entity.Resume
import java.time.LocalDate
import java.time.LocalDateTime

data class ExperienceDto(
    val experienceId: Int? = null,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createdAt: LocalDateTime? = null
)
{
    fun toExperience(resume: Resume): Experience = Experience(
        experienceId = this.experienceId ?: 0,
        resume = resume,
        description = this.description,
        startDate = this.startDate,
        endDate = this.endDate,
        createdAt = this.createdAt ?: LocalDateTime.now()
    )

    companion object {
        fun fromEntity(experience: Experience): ExperienceDto = ExperienceDto(
            experienceId = experience.experienceId,
            description = experience.description,
            startDate = experience.startDate,
            endDate = experience.endDate,
            createdAt = experience.createdAt
        )
    }
}