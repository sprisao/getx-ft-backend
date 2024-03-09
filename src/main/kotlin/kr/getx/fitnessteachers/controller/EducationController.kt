package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.Education
import kr.getx.fitnessteachers.service.EducationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/educations")
class EducationController(private val educationService: EducationService) {

    @GetMapping("/all")
    fun getAllEducations(): List<Education> = educationService.getAllEducations()

    @PostMapping("/add")
    fun addEducation(@RequestBody education: Education): Education = educationService.addEducation(education)

    @GetMapping("/{id}")
    fun getEducation(@PathVariable id: Int): Education? = educationService.getEducationById(id)

    @DeleteMapping("/delete/{id}")
    fun deleteEducation(@PathVariable id: Int) = educationService.deleteEducation(id)
}
