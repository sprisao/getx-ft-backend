package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.Certification
import kr.getx.fitnessteachers.repository.CertificationRepository
import org.springframework.stereotype.Service

@Service
class CertificationService(private val certificationRepository: CertificationRepository) {

    fun getAllCertifications(): List<Certification> = certificationRepository.findAll()

    fun addCertification(certification: Certification): Certification = certificationRepository.save(certification)

    fun getCertificationById(id: Int): Certification? = certificationRepository.findById(id).orElse(null)

    fun updateCertification(certification: Certification): Certification = certificationRepository.save(certification)

    fun deleteCertification(id: Int) = certificationRepository.deleteById(id)
}
