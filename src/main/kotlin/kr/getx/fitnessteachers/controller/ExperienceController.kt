package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.ExperienceDto
import kr.getx.fitnessteachers.service.ExperienceService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/experiences")
class ExperienceController(private val experienceService: ExperienceService) {

    @GetMapping("/all")
    fun getAllExperiences(): ResponseEntity<List<ExperienceDto>> =
        ResponseEntity.ok(experienceService.getAllExperiences().map(ExperienceDto::fromEntity))

    @GetMapping("/user/{userId}")
    fun findExperiencesByIds(@PathVariable userId: Int): ResponseEntity<List<ExperienceDto>> =
        ResponseEntity.ok(experienceService.findExperiencesByUserIds(userId).map(ExperienceDto::fromEntity))

    @PostMapping("/add")
    fun addExperience(@RequestBody experienceDto: List<ExperienceDto>): ResponseEntity<List<ExperienceDto>> =
        ResponseEntity.ok(experienceService.addExperiences(experienceDto).map(ExperienceDto::fromEntity))

    @PutMapping("/update")
    fun updateExperiences(@RequestBody experienceDto: List<ExperienceDto>): ResponseEntity<List<ExperienceDto>> =
        ResponseEntity.ok(experienceService.updateExperiences(experienceDto).map(ExperienceDto::fromEntity))

    @DeleteMapping("/delete")
    fun deleteExperience(@RequestBody experienceIds: List<Int>): ResponseEntity<Void> {
        experienceService.deleteExperiences(experienceIds)
        return ResponseEntity.noContent().build()
    }
}
