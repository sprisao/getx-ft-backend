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

    fun addCertifications(certificationDtos: List<CertificationDto>): List<Certification> {
        return certificationDtos.map { dto ->
            val user = userRepository.findById(dto.userId).orElseThrow {
                IllegalArgumentException("해당 유저를 찾을수 없습니다 !! userId : ${dto.userId}")
            }
            certificationRepository.save(
                Certification(
                    certificationId = dto.certificationId ?: 0,
                    user = user,
                    name = dto.name,
                    issuedBy = dto.issuedBy,
                    issuedDate = dto.issuedDate,
                    createdAt = dto.createdAt ?: LocalDateTime.now()
                )
            )
        }
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
        certificationRepository.deleteAllById(certificationIds)
    }

    fun findCertificationsByIds(certificationIds: List<Int>): List<Certification> = certificationRepository.findByCertificationIdIn(certificationIds)
}
