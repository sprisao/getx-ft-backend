package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.CertificationDto
import kr.getx.fitnessteachers.entity.Certification
import kr.getx.fitnessteachers.repository.CertificationRepository
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.time.LocalDateTime

@Service
class CertificationService(
    private val certificationRepository: CertificationRepository,
    private val userRepository: UserRepository,
) {

    fun getAllCertifications(): List<Certification> = certificationRepository.findAll()

    fun findCertificationsByUserIds(userId: Int): List<Certification> {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("해당 유저를 찾을수 없습니다 !! userId : $userId")
        }
        return certificationRepository.findByUser(user)
    }
    fun syncCertifications(certificationDtos: List<CertificationDto>): List<Certification> {
        val existingCertifications = certificationRepository.findAll().associateBy { it.certificationId }
        val certificationIdsToKeep = mutableSetOf<Int>()
        val syncedCertifications = mutableListOf<Certification>()

        certificationDtos.forEach { dto ->
            if (dto.certificationId == null) {
                // 새로운 데이터 추가
                val user = userRepository.findById(dto.userId).orElseThrow {
                    IllegalArgumentException("해당 유저를 찾을 수 없습니다 !! userId : ${dto.userId}")
                }
                val newCertification = Certification(
                    user = user,
                    name = dto.name,
                    issuedBy = dto.issuedBy,
                    issuedDate = dto.issuedDate,
                    isDeleted = false,
                    createdAt = LocalDateTime.now()
                )
                val savedCertification = certificationRepository.save(newCertification)
                syncedCertifications.add(savedCertification)
                certificationIdsToKeep.add(savedCertification.certificationId)
            } else {
                // 기존 데이터 업데이트
                val certification = existingCertifications[dto.certificationId]?.apply {
                    name = dto.name
                    issuedBy = dto.issuedBy
                    issuedDate = dto.issuedDate
                }
                if (certification != null) {
                    syncedCertifications.add(certificationRepository.save(certification))
                    certificationIdsToKeep.add(certification.certificationId)
                }
            }
        }

        // 요청에 포함되지 않은 데이터 삭제
        val certificationIdsToDelete = existingCertifications.keys - certificationIdsToKeep
        if (certificationIdsToDelete.isNotEmpty()) {
            certificationRepository.deleteAllById(certificationIdsToDelete)
        }

        return syncedCertifications
    }

    fun updateCertifications(certificationDtos: List<CertificationDto>): List<Certification> {
        val certificationIds = certificationDtos.mapNotNull { it.certificationId }
        val existingCertifications = certificationRepository.findByCertificationIdIn(certificationIds).associateBy { it.certificationId }

        return certificationDtos.mapNotNull { dto ->
            val certification = existingCertifications[dto.certificationId]?.apply {
                name = dto.name
                issuedBy = dto.issuedBy
                issuedDate = dto.issuedDate
            } ?: return@mapNotNull null
            certificationRepository.save(certification)
        }
    }

    fun deleteCertifications(certificationIds: List<Int>) {
        val certifications = certificationRepository.findAllByCertificationIdInAndIsDeletedFalse(certificationIds)
    if (certifications.isEmpty()) {
        throw IllegalArgumentException("No certifications found for the given IDs")
    }
    certifications.forEach { certification ->
        certification.isDeleted = true
        certification.isDeletedAt = LocalDateTime.now()
    }
    certificationRepository.saveAll(certifications)
    }

    fun findCertificationsByIds(certificationIds: List<Int>): List<Certification> = certificationRepository.findByCertificationIdIn(certificationIds)
}
