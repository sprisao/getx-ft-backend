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
    fun addEducations(educationDtos: List<EducationDto>): List<Education> {
        return educationDtos.map { dto ->
            val user = userRepository.findById(dto.userId).orElseThrow {
                IllegalArgumentException("해당 유저를 찾을수 없습니다 !! userId : ${dto.userId}")
            }
            educationRepository.save(
                Education(
                    educationId = dto.educationId ?: 0,
                    user = user,
                    courseName = dto.courseName,
                    institution = dto.institution,
                    completionDate = dto.completionDate,
                    createdAt = dto.createdAt ?: LocalDateTime.now()
                )
            )
        }
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

