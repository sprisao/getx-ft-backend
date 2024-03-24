package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.Certification
import kr.getx.fitnessteachers.entity.Resume
import java.time.LocalDate
import java.time.LocalDateTime


data class CertificationDto(
    val certificationId: Int,
    val name: String,
    val issuedBy: String,
    val issuedDate: LocalDate,
    val createdAt: LocalDateTime?
)
{
    fun toCertification(resume: Resume): Certification = Certification(
        certificationId = this.certificationId,
        resume = resume,
        name = this.name,
        issuedBy = this.issuedBy,
        issuedDate = this.issuedDate,
        createdAt = this.createdAt ?: LocalDateTime.now()
    )

    companion object {
        fun fromEntity(certification: Certification): CertificationDto = CertificationDto(
            certificationId = certification.certificationId,
            name = certification.name,
            issuedBy = certification.issuedBy,
            issuedDate = certification.issuedDate,
            createdAt = certification.createdAt
        )
    }
}