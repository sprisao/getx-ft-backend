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

    @PostMapping("/add")
    fun addEducation(@RequestBody educationDto: List<EducationDto>): ResponseEntity<List<EducationDto>> =
        ResponseEntity.ok(educationService.addEducations(educationDto).map(EducationDto::fromEntity))

    @PutMapping("/update/{userId}")
    fun updateEducations(@RequestBody educationDtos: List<EducationDto>): ResponseEntity<List<EducationDto>> =
        ResponseEntity.ok(educationService.updateEducations(educationDtos).map(EducationDto::fromEntity))

    @DeleteMapping("/delete/{educationId}")
    fun deleteEducations(@RequestBody educationIds: List<Int>): ResponseEntity<Void> {
        educationService.deleteEducations(educationIds)
        return ResponseEntity.noContent().build()
    }
}
