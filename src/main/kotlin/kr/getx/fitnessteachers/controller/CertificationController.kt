package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.Certification
import kr.getx.fitnessteachers.service.CertificationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/certifications")
class CertificationController(private val certificationService: CertificationService) {

    @GetMapping
    fun getAllCertifications(): List<Certification> = certificationService.getAllCertifications()

    @PostMapping("/add")
    fun addCertification(@RequestBody certification: Certification): Certification = certificationService.addCertification(certification)

    @GetMapping("/{id}")
    fun getCertification(@PathVariable id: Int): Certification? = certificationService.getCertificationById(id)

    @PutMapping("/update")
    fun updateCertification(@RequestBody certification: Certification): Certification = certificationService.updateCertification(certification)

    @DeleteMapping("/delete/{id}")
    fun deleteCertification(@PathVariable id: Int) = certificationService.deleteCertification(id)
}
