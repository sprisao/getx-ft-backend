package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.CertificationDto
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.service.CertificationService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/certifications")
class CertificationController(private val certificationService: CertificationService) {

    @GetMapping("/all")
    fun getAllCertifications(): ResponseEntity<List<CertificationDto>> =
        ResponseEntity.ok(certificationService.getAllCertifications().map(CertificationDto::fromEntity))

    @PostMapping("/add")
    fun addCertification(@RequestBody certificationDto: CertificationDto, @RequestBody resume: Resume): ResponseEntity<CertificationDto> =
        ResponseEntity.ok(CertificationDto.fromEntity(certificationService.addCertification(certificationDto, resume)))

    @GetMapping("/{certificationId}")
    fun getCertification(@PathVariable certificationId: Int): ResponseEntity<CertificationDto> =
        certificationService.getCertificationById(certificationId)?.let { ResponseEntity.ok(CertificationDto.fromEntity(it)) }
            ?: ResponseEntity.notFound().build()

    @DeleteMapping("/delete/{certificationId}")
    fun deleteCertification(@PathVariable certificationId: Int): ResponseEntity<Void> {
        certificationService.deleteCertification(certificationId)
        return ResponseEntity.noContent().build()
    }
}
