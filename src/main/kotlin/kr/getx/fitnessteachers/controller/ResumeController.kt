package kr.getx.fitnessteachers.controller

import jakarta.servlet.http.HttpServletRequest
import kr.getx.fitnessteachers.dto.ResumeData
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.service.ResumeService
import kr.getx.fitnessteachers.service.UserService
import kr.getx.fitnessteachers.utils.StringConversionUtils
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/resumes")
class ResumeController(private val resumeService: ResumeService, private val userService: UserService) {

    @GetMapping
    fun getAllResumes(): List<Resume> = resumeService.getAllResumes()

    @PostMapping("/add")
    fun addResume(@RequestBody resumeData: ResumeData, request: HttpServletRequest): ResponseEntity<Resume> {
        val user = userService.findUserById(resumeData.userId)

        val photoString = StringConversionUtils.convertListToString(resumeData.photos)
        val resume = Resume(
                user = user,
                photos = photoString
        )

        val saveResume = resumeService.addResume(resume)
        return ResponseEntity.ok(saveResume)
    }

    @GetMapping("/user/{userId}")
    fun getResumeByUserId(@PathVariable userId: Int): ResponseEntity<ResumeData> {
        val user = userService.findUserById(userId)
        if (user!= null) {
            val resumes = resumeService.getResumeByUserId(user.userId)

            val resumeData = resumes?.let {
                ResumeData(
                    photos = it.photos?.let { StringConversionUtils.convertStringToList(it) } ?: emptyList(),
                    userId = it.user!!.userId ?: 0
                )
            }

            return ResponseEntity.ok().body(resumeData)
        } else {
            return ResponseEntity.notFound().build()
        }
    }
    @GetMapping("/{id}")
    fun getResume(@PathVariable id: Int): Resume? = resumeService.getResumeById(id)

    @PutMapping("/update")
    fun updateResume(@RequestBody resume: Resume): Resume = resumeService.updateResume(resume)


    @DeleteMapping("/delete/{id}")
    fun deleteResume(@PathVariable id: Int) = resumeService.deleteResume(id)

}
