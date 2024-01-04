package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.service.ResumeService
import kr.getx.fitnessteachers.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/resumes")
class ResumeController(private val resumeService: ResumeService) {

    @GetMapping
    fun getAllResumes(): List<Resume> = resumeService.getAllResumes()

    @PostMapping("/add")
    fun addResume(@RequestBody resume: Resume): Resume = resumeService.addResume(resume)

    @GetMapping("/{id}")
    fun getResume(@PathVariable id: Int): Resume? = resumeService.getResumeById(id)

    @PutMapping("/update")
    fun updateResume(@RequestBody resume: Resume): Resume = resumeService.updateResume(resume)

    @DeleteMapping("/delete/{id}")
    fun deleteResume(@PathVariable id: Int) = resumeService.deleteResume(id)
}
