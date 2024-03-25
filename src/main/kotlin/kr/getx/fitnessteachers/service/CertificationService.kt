package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.CertificationDto
import kr.getx.fitnessteachers.entity.Certification
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.repository.CertificationRepository
import org.springframework.stereotype.Service
import kr.getx.fitnessteachers.exceptions.ResumeNotFoundException

@Service
class CertificationService(
    private val certificationRepository: CertificationRepository,
) {

    fun getAllCertifications(): List<Certification> = certificationRepository.findAll()

    fun getCertificationById(id: Int): Certification? = certificationRepository.findById(id).orElse(null)

    fun addCertification(certificationDto: CertificationDto, resume: Resume): Certification = certificationRepository.save(certificationDto.toCertification(resume))

    fun addCertificationForResume(resume: Resume, certificationDto: CertificationDto): Certification = certificationRepository.save(certificationDto.toCertification(resume))

    fun updateCertification(resume: Resume, newCertifications: List<Certification>) {
        val existingCertifications = certificationRepository.findByResumeResumeId(resume.resumeId).associateBy { it.certificationId }

        // 새 자격증 정보를 반복하며 업데이트 또는 추가
        newCertifications.forEach { newCert ->
            val existingCert = existingCertifications[newCert.certificationId]
            certificationRepository.save(existingCert?.apply {
                name = newCert.name
                issuedBy = newCert.issuedBy
                issuedDate = newCert.issuedDate
            } ?: newCert.apply { this.resume = resume })
        }

        // 더 이상 존재하지 않는 자격증 정보 삭제
        existingCertifications.values.forEach {
            if (newCertifications.none { newCert -> newCert.certificationId == it.certificationId }) {
                certificationRepository.delete(it)
            }
        }

    }

    fun deleteCertification(id: Int) = certificationRepository.deleteById(id)

    fun getCertificationByResumeId(resumeId: Int): List<Certification> = certificationRepository.findByResumeResumeId(resumeId)

    fun deleteAllByResume(resume: Resume) = certificationRepository.deleteAllByResume(resume)
}
