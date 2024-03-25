package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.ExperienceDto
import kr.getx.fitnessteachers.dto.ResumeDto
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.service.ExperienceService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/experiences")
class ExperienceController(private val experienceService: ExperienceService) {

    @GetMapping("/all")
    fun getAllExperiences(): ResponseEntity<List<ExperienceDto>> =
        ResponseEntity.ok(experienceService.getAllExperiences().map(ExperienceDto::fromEntity))

    @PostMapping("/add")
    fun addExperience(@RequestBody experienceDto: ExperienceDto, @RequestBody resume: Resume): ResponseEntity<ExperienceDto> =
        ResponseEntity.ok(ExperienceDto.fromEntity(experienceService.addExperience(experienceDto, resume)))

    @GetMapping("/{id}")
    fun getExperience(@PathVariable id: Int): ResponseEntity<ExperienceDto> =
        experienceService.getExperienceById(id)?.let { ResponseEntity.ok(ExperienceDto.fromEntity(it)) }
            ?: ResponseEntity.notFound().build()


    @DeleteMapping("/delete/{id}")
    fun deleteExperience(@PathVariable id: Int): ResponseEntity<Void> {
        experienceService.deleteExperience(id)
        return ResponseEntity.noContent().build()
    }
}
