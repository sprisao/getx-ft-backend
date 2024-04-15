package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.CertificationDto
import kr.getx.fitnessteachers.service.CertificationService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/certifications")
class CertificationController(private val certificationService: CertificationService) {

    @GetMapping("/all")
    fun getAllCertifications(): ResponseEntity<List<CertificationDto>> =
        ResponseEntity.ok(certificationService.getAllCertifications().map(CertificationDto::fromEntity))

    @GetMapping("/user/{userId}")
    fun findCertificationsByIds(@PathVariable userId: Int): ResponseEntity<List<CertificationDto>> =
        ResponseEntity.ok(certificationService.findCertificationsByUserIds(userId).map(CertificationDto::fromEntity))

    @PostMapping("/add")
    fun addCertification(@RequestBody certificationDto: List<CertificationDto>): ResponseEntity<List<CertificationDto>> =
        ResponseEntity.ok(certificationService.addCertifications(certificationDto).map(CertificationDto::fromEntity))

    @PutMapping ("/update/{userId}")
    fun updateCertifications(@RequestBody certificationDtos: List<CertificationDto>): ResponseEntity<List<CertificationDto>> =
        ResponseEntity.ok(certificationService.updateCertifications(certificationDtos).map(CertificationDto::fromEntity))

    @DeleteMapping("/delete/{certificationId}")
    fun deleteCertifications(@RequestBody certificationIds: List<Int>): ResponseEntity<Void> {
        certificationService.deleteCertifications(certificationIds)
        return ResponseEntity.noContent().build()
    }
}
