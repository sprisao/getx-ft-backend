package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.EducationDto
import kr.getx.fitnessteachers.entity.Education
import kr.getx.fitnessteachers.repository.EducationRepository
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EducationService(
    private val educationRepository: EducationRepository,
    private val userRepository: UserRepository,
) {

    fun getAllEducations(): List<Education> = educationRepository.findAll()

    fun findEducationsByUserIds(userId: Int): List<Education> {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("해당 유저를 찾을수 없습니다 !! userId : $userId")
        }
        return educationRepository.findByUser(user)
    }

    fun syncEducations(educationDtos: List<EducationDto>): List<Education> {
        val existingEducations = educationRepository.findAll().associateBy { it.educationId }
        val educationIdsToKeep = mutableSetOf<Int>()
        val syncedEducations = mutableListOf<Education>()

        educationDtos.forEach { dto ->
            if (dto.educationId == null) {
                // 새로운 데이터 추가
                val user = userRepository.findById(dto.userId).orElseThrow {
                    IllegalArgumentException("해당 유저를 찾을 수 없습니다 !! userId : ${dto.userId}")
                }
                val newEducation = Education(
                    user = user,
                    courseName = dto.courseName,
                    institution = dto.institution,
                    completionDate = dto.completionDate,
                    createdAt = LocalDateTime.now()
                )
                val savedEducation = educationRepository.save(newEducation)
                syncedEducations.add(savedEducation)
                educationIdsToKeep.add(savedEducation.educationId)
            } else {
                // 기존 데이터 업데이트
                existingEducations[dto.educationId]?.let { education ->
                    education.apply {
                        courseName = dto.courseName
                        institution = dto.institution
                        completionDate = dto.completionDate
                    }
                    val updatedEducation = educationRepository.save(education)
                    syncedEducations.add(updatedEducation)
                    educationIdsToKeep.add(updatedEducation.educationId)
                }
            }
        }

        // 요청에 포함되지 않은 데이터 삭제
        val educationIdsToDelete = existingEducations.keys - educationIdsToKeep
        if(educationIdsToDelete.isNotEmpty()) {
            educationRepository.deleteAllById(educationIdsToDelete)
        }

        return syncedEducations
    }

    fun updateEducations(educationDtos: List<EducationDto>): List<Education> {
        val educationIds = educationDtos.mapNotNull { it.educationId }
        val existingEducations = educationRepository.findByEducationIdIn(educationIds).associateBy { it.educationId }

        return educationDtos.mapNotNull { dto ->
            val education = existingEducations[dto.educationId]?.apply {
                courseName = dto.courseName
                institution = dto.institution
                completionDate = dto.completionDate
            } ?: return@mapNotNull null
            educationRepository.save(education)
        }
    }

    fun deleteEducations(educationIds: List<Int>) {
        educationRepository.deleteAllById(educationIds)
    }

    fun findEducationsByIds(educationIds: List<Int>): List<Education> = educationRepository.findByEducationIdIn(educationIds)
}

