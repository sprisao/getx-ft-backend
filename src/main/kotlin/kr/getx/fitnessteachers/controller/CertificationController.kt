package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.CertificationDto
import kr.getx.fitnessteachers.entity.Certification
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
    fun addCertification(@RequestBody certificationDto: CertificationDto, @RequestParam resumeId: Int): ResponseEntity<CertificationDto> =
        ResponseEntity.ok(CertificationDto.fromEntity(certificationService.addCertification(certificationDto, resumeId)))

    @GetMapping("/{id}")
    fun getCertification(@PathVariable id: Int): ResponseEntity<CertificationDto> =
        certificationService.getCertificationById(id)?.let { ResponseEntity.ok(CertificationDto.fromEntity(it)) }
            ?: ResponseEntity.notFound().build()

    @DeleteMapping("/delete/{id}")
    fun deleteCertification(@PathVariable id: Int): ResponseEntity<Void> {
        certificationService.deleteCertification(id)
        return ResponseEntity.noContent().build()
    }
}
