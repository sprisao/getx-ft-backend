package kr.getx.fitnessteachers.controller

import jakarta.servlet.http.HttpServletRequest
import kr.getx.fitnessteachers.dto.CertificationDto
import kr.getx.fitnessteachers.dto.EducationDto
import kr.getx.fitnessteachers.dto.ExperienceDto
import kr.getx.fitnessteachers.dto.ResumeDto
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.service.*
import kr.getx.fitnessteachers.utils.StringConversionUtils
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/resumes")
class ResumeController(
    private val resumeService: ResumeService,
    private val userService: UserService,
    private val experienceService: ExperienceService,
    private val certificationService: CertificationService,
    private val educationService: EducationService
) {

    @GetMapping
    fun getAllResumes(): List<Resume> = resumeService.getAllResumes()

    @PostMapping("/add")
    fun addResume(@RequestBody resumeDto: ResumeDto, request: HttpServletRequest): ResponseEntity<Resume> {
       val saveResume = resumeService.addResumeWithDetails(resumeDto)
        return ResponseEntity.ok(saveResume)
    }

    @GetMapping("/user/{userId}")
    fun getResumeByUserId(@PathVariable userId: Int): ResponseEntity<ResumeDto> {
        val user = userService.findUserById(userId)
        return if (user!= null) {
            val resume = resumeService.getResumeByUserId(user.userId)
            if (resume != null) {
                val resumeDto = ResumeDto(
                    userId = user.userId,
                    photos = StringConversionUtils.convertStringToList(resume.photos ?: " "),
                    experiences = experienceService.getExperienceByResumeId(resume.resumeId)
                        .map { ExperienceDto(it.description, it.startDate, it.endDate) },
                    educations = educationService.getEducationByResumeId(resume.resumeId)
                        .map { EducationDto(it.courseName, it.institution, it.completionDate) },
                    certifications = certificationService.getCertificationByResumeId(resume.resumeId)
                        .map { CertificationDto(it.name, it.issuedBy, it.issuedDate) }
                )
                return ResponseEntity.ok().body(resumeDto)
            } else {
                ResponseEntity.notFound().build()
            }
        } else {
            ResponseEntity.notFound().build()
        }
    }
    @GetMapping("/{id}")
    fun getResume(@PathVariable id: Int): Resume? = resumeService.getResumeById(id)

    @PutMapping("/update")
    fun updateResume(@RequestBody resume: Resume): Resume = resumeService.updateResume(resume)

    @DeleteMapping("/delete/{id}")
    fun deleteResume(@PathVariable id: Int) = resumeService.deleteResume(id)
}
