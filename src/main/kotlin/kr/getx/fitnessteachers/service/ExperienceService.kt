package kr.getx.fitnessteachers.service

import java.time.LocalDateTime
import kr.getx.fitnessteachers.dto.ExperienceDto
import kr.getx.fitnessteachers.entity.Experience
import kr.getx.fitnessteachers.repository.ExperienceRepository
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ExperienceService(
        private val experienceRepository: ExperienceRepository,
        private val userRepository: UserRepository,
) {

    fun getAllExperiences(): List<Experience> = experienceRepository.findAll()

    fun findExperiencesByUserIds(userId: Int): List<Experience> {
        val user =
                userRepository.findById(userId).orElseThrow {
                    IllegalArgumentException("해당 유저를 찾을수 없습니다 !! userId : $userId")
                }
        return experienceRepository.findByUser(user)
    }
    fun syncExperiences(experienceDtos: List<ExperienceDto>): List<Experience> {
        val existingExperiences = experienceRepository.findAll().associateBy { it.experienceId }
        val experienceIdsToKeep = mutableSetOf<Int>()
        val syncedExperiences = mutableListOf<Experience>()

        experienceDtos.forEach { dto ->
            if (dto.experienceId == null) {
                // 새로운 데이터 추가
                val user =
                        userRepository.findById(dto.userId).orElseThrow {
                            IllegalArgumentException("해당 유저를 찾을 수 없습니다 !! userId : ${dto.userId}")
                        }
                val newExperience =
                        Experience(
                                user = user,
                                description = dto.description,
                                startDate = dto.startDate,
                                endDate = dto.endDate,
                                isDeleted = false,
                                createdAt = LocalDateTime.now()
                        )
                val savedExperience = experienceRepository.save(newExperience)
                syncedExperiences.add(savedExperience)
                experienceIdsToKeep.add(savedExperience.experienceId)
            } else {
                // 기존 데이터 업데이트
                existingExperiences[dto.experienceId]?.let { experience ->
                    experience.apply {
                        description = dto.description
                        startDate = dto.startDate
                        endDate = dto.endDate
                    }
                    val updatedExperience = experienceRepository.save(experience)
                    syncedExperiences.add(updatedExperience)
                    experienceIdsToKeep.add(updatedExperience.experienceId)
                }
            }
        }

        // 요청에 포함되지 않은 데이터 삭제
        val experienceIdsToDelete = existingExperiences.keys - experienceIdsToKeep
        if (experienceIdsToDelete.isNotEmpty()) {
            experienceRepository.deleteAllById(experienceIdsToDelete)
        }

        return syncedExperiences
    }

    fun updateExperiences(experienceDto: List<ExperienceDto>): List<Experience> {
        val experienceIds = experienceDto.mapNotNull { it.experienceId }
        val existingExperiences =
                experienceRepository.findByExperienceIdIn(experienceIds).associateBy {
                    it.experienceId
                }

        return experienceDto.mapNotNull { dto ->
            val experience =
                    existingExperiences[dto.experienceId]?.apply {
                        description = dto.description
                        startDate = dto.startDate
                        endDate = dto.endDate
                    }
                            ?: return@mapNotNull null
            experienceRepository.save(experience)
        }
    }

    fun deleteExperiences(experienceIds: List<Int>) {
        val experiences =
                experienceRepository.findAllByExperienceIdInAndIsDeletedFalse(experienceIds)
        if (experiences.isEmpty()) {
            throw IllegalArgumentException("No experiences found for the given IDs")
        }
        experiences.forEach { experience ->
            experience.isDeleted = true
            experience.isDeletedAt = LocalDateTime.now()
        }
        experienceRepository.saveAll(experiences)
    }

    fun findExperiencesByIds(experienceIds: List<Int>): List<Experience> =
            experienceRepository.findByExperienceIdIn(experienceIds)
}
