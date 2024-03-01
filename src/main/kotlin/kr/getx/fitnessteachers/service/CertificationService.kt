package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.Certification
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.repository.CertificationRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CertificationService(private val certificationRepository: CertificationRepository) {

    fun getAllCertifications(): List<Certification> = certificationRepository.findAll()

    fun addCertification(certification: Certification): Certification = certificationRepository.save(certification)

    fun getCertificationById(id: Int): Certification? = certificationRepository.findById(id).orElse(null)

    fun updateCertification(resume: Resume,certification: Certification) {
        val certificationToUpdate = certificationRepository.findByResumeResumeId(resume.resumeId)

        certificationToUpdate.forEach { certificationToUpdate ->
            if(certificationToUpdate.certificationId == certification.certificationId) {
                certificationToUpdate.apply {
                    this.name = certification.name
                    this.issuedBy = certification.issuedBy
                    this.issuedDate = certification.issuedDate
                }
                certificationRepository.save(certificationToUpdate)
            }
        }
    }

    fun deleteCertification(id: Int) = certificationRepository.deleteById(id)

    fun addCertification(resume: Resume, certification: Certification): ResponseEntity<Map<String, Any>> {
        return try {
            certificationRepository.save(certification)
            ResponseEntity.ok(mapOf("status" to true, "data" to certification))
        } catch (e : Exception) {
            ResponseEntity.badRequest().body(mapOf("status" to false, "data" to e.message as Any))
        }
    }

    fun getCertificationByResumeId(resumeId: Int): List<Certification> {
        return certificationRepository.findByResumeResumeId(resumeId)
    }

    fun deleteAllByResume(resume: Resume) {
        certificationRepository.deleteAllByResume(resume)
    }
}
