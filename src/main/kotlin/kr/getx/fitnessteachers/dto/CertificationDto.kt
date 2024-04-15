package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.Certification
import java.time.LocalDate
import java.time.LocalDateTime


data class CertificationDto(
    val certificationId: Int? = null,
    val userId: Int,
    val name: String,
    val issuedBy: String,
    val issuedDate: LocalDate,
    val createdAt: LocalDateTime? = null
)
{
    companion object {
        fun fromEntity(certification: Certification): CertificationDto = CertificationDto(
            certificationId = certification.certificationId,
            userId = certification.user.userId,
            name = certification.name,
            issuedBy = certification.issuedBy,
            issuedDate = certification.issuedDate,
            createdAt = certification.createdAt
        )
    }
}