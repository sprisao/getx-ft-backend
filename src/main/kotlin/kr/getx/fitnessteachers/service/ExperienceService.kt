package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.ExperienceDto
import kr.getx.fitnessteachers.entity.Experience
import kr.getx.fitnessteachers.repository.ExperienceRepository
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ExperienceService(
    private val experienceRepository: ExperienceRepository,
    private val userRepository: UserRepository,
) {

    fun getAllExperiences(): List<Experience> = experienceRepository.findAll()

    fun findExperiencesByUserIds(userId: Int): List<Experience> {
        val user = userRepository.findById(userId).orElseThrow {
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
                val user = userRepository.findById(dto.userId).orElseThrow {
                    IllegalArgumentException("해당 유저를 찾을 수 없습니다 !! userId : ${dto.userId}")
                }
                val newExperience = Experience(
                    user = user,
                    description = dto.description,
                    startDate = dto.startDate,
                    endDate = dto.endDate,
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
//    fun addExperiences(experienceDto: List<ExperienceDto>): List<Experience> {
//        return experienceDto.map { dto ->
//            val user = userRepository.findById(dto.userId).orElseThrow {
//                IllegalArgumentException("해당 유저를 찾을수 없습니다 !! userId : ${dto.userId}")
//            }
//            experienceRepository.save(
//                Experience(
//                    experienceId = dto.experienceId ?: 0,
//                    user = user,
//                    description = dto.description,
//                    startDate = dto.startDate,
//                    endDate = dto.endDate,
//                    createdAt = dto.createdAt ?: LocalDateTime.now()
//                )
//            )
//        }
//    }

    fun updateExperiences(experienceDto: List<ExperienceDto>): List<Experience> {
        val experienceIds = experienceDto.mapNotNull { it.experienceId }
        val existingExperiences = experienceRepository.findByExperienceIdIn(experienceIds).associateBy { it.experienceId }

        return experienceDto.mapNotNull { dto ->
            val experience = existingExperiences[dto.experienceId]?.apply {
                description = dto.description
                startDate = dto.startDate
                endDate = dto.endDate
            } ?: return@mapNotNull null
            experienceRepository.save(experience)
        }
    }

    fun deleteExperiences(experienceIds: List<Int>) {
        experienceRepository.deleteAllById(experienceIds)
    }

    fun findExperiencesByIds(experienceIds: List<Int>): List<Experience> = experienceRepository.findByExperienceIdIn(experienceIds)
}
