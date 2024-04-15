package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.Experience
import kr.getx.fitnessteachers.entity.Resume
import java.time.LocalDate
import java.time.LocalDateTime

data class ExperienceDto(
    val experienceId: Int? = null,
    val userId: Int,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createdAt: LocalDateTime? = null
)
{
    companion object {
        fun fromEntity(experience: Experience): ExperienceDto = ExperienceDto(
            experienceId = experience.experienceId,
            userId = experience.user.userId,
            description = experience.description,
            startDate = experience.startDate,
            endDate = experience.endDate,
            createdAt = experience.createdAt
        )
    }
}