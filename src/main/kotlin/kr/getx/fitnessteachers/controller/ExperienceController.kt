package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.Experience
import kr.getx.fitnessteachers.service.ExperienceService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/experiences")
class ExperienceController(private val experienceService: ExperienceService) {

    @GetMapping("/all")
    fun getAllExperiences(): List<Experience> = experienceService.getAllExperiences()

    @PostMapping("/add")
    fun addExperience(@RequestBody experience: Experience): Experience = experienceService.addExperience(experience)

    @GetMapping("/{id}")
    fun getExperience(@PathVariable id: Int): Experience? = experienceService.getExperienceById(id)

    @DeleteMapping("/delete/{id}")
    fun deleteExperience(@PathVariable id: Int) = experienceService.deleteExperience(id)
}
