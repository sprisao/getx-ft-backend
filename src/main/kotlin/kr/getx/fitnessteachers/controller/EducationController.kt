package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.EducationDto
import kr.getx.fitnessteachers.service.EducationService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/educations")
class EducationController(private val educationService: EducationService) {

    @GetMapping("/all")
    fun getAllEducations(): ResponseEntity<List<EducationDto>> =
        ResponseEntity.ok(educationService.getAllEducations().map(EducationDto::fromEntity))

    @GetMapping("/user/{userId}")
    fun findEducationsByIds(@PathVariable userId: Int): ResponseEntity<List<EducationDto>> =
        ResponseEntity.ok(educationService.findEducationsByUserIds(userId).map(EducationDto::fromEntity))

    @PostMapping("/sync")
    fun syncEducation(@RequestBody educationDto: List<EducationDto>): ResponseEntity<List<EducationDto>> =
        ResponseEntity.ok(educationService.syncEducations(educationDto).map(EducationDto::fromEntity))

    @PutMapping("/update")
    fun updateEducations(@RequestBody educationDtos: List<EducationDto>): ResponseEntity<List<EducationDto>> =
        ResponseEntity.ok(educationService.updateEducations(educationDtos).map(EducationDto::fromEntity))

    @DeleteMapping("/delete")
    fun deleteEducations(@RequestBody educationIds: List<Int>): ResponseEntity<Void> {
        educationService.deleteEducations(educationIds)
        return ResponseEntity.noContent().build()
    }
}
