package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.Experience
import kr.getx.fitnessteachers.service.ExperienceService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/experiences")
class ExperienceController(private val experienceService: ExperienceService) {

    @GetMapping
    fun getAllExperiences(): List<Experience> = experienceService.getAllExperiences()

    @PostMapping("/add")
    fun addExperience(@RequestBody experience: Experience): Experience = experienceService.addExperience(experience)

    @GetMapping("/{id}")
    fun getExperience(@PathVariable id: Int): Experience? = experienceService.getExperienceById(id)

    @PutMapping("/update")
    fun updateExperience(@RequestBody experience: Experience): Experience = experienceService.updateExperience(experience)

    @DeleteMapping("/delete/{id}")
    fun deleteExperience(@PathVariable id: Int) = experienceService.deleteExperience(id)
}
