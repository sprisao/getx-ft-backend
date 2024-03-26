package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.EducationDto
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.service.EducationService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/educations")
class EducationController(private val educationService: EducationService) {

    @GetMapping("/all")
    fun getAllEducations(): ResponseEntity<List<EducationDto>> =
        ResponseEntity.ok(educationService.getAllEducations().map(EducationDto::fromEntity))

    @PostMapping("/add")
    fun addEducation(@RequestBody educationDto: EducationDto, @RequestBody resume: Resume): ResponseEntity<EducationDto> =
        ResponseEntity.ok(EducationDto.fromEntity(educationService.addEducation(educationDto, resume)))

    @GetMapping("/{educationId}")
    fun getEducation(@PathVariable educationId : Int): ResponseEntity<EducationDto> =
        educationService.getEducationById(educationId)?.let { ResponseEntity.ok(EducationDto.fromEntity(it)) }
            ?: ResponseEntity.notFound().build()

    @DeleteMapping("/delete/{educationId}")
        fun deleteEducation(@PathVariable educationId: Int): ResponseEntity<Void> {
            educationService.deleteEducation(educationId)
            return ResponseEntity.noContent().build()
        }
}
