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

    @GetMapping("/{userId}")
    fun getResumeByUserId(@PathVariable userId: Int): ResponseEntity<ResumeDto> {
        val user = userService.findUserById(userId)
        return if (user != null) {
            val resume = resumeService.getResumeByUserId(user.userId)
            if (resume != null) {
                val resumeDto = ResumeDto(
                    resumeId = resume.resumeId,
                    userId = user.userId,
                    photos = StringConversionUtils.convertStringToList(resume.photos ?: " "),
                    experiences = experienceService.getExperienceByResumeId(resume.resumeId)
                        .map { ExperienceDto(it.experienceId, it.description, it.startDate, it.endDate) },
                    educations = educationService.getEducationByResumeId(resume.resumeId)
                        .map { EducationDto(it.educationId, it.courseName, it.institution, it.completionDate) },
                    certifications = certificationService.getCertificationByResumeId(resume.resumeId)
                        .map { CertificationDto(it.certificationId, it.name, it.issuedBy, it.issuedDate) }
                )
                return ResponseEntity.ok().body(resumeDto)
            } else {
                ResponseEntity.notFound().build()
            }
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/update/{userId}")
    fun updateResumeByUserId(@PathVariable userId: Int, @RequestBody resumeDto: ResumeDto): ResponseEntity<Any> {
        val user = userService.findUserById(userId)
        return if (user != null && resumeDto != null) {
            // 이력서 업데이트를 위한 데이터 검증 및 설정
            resumeDto.userId = userId

            try {
                val updatedResume = resumeService.updateResumeWithDetails(resumeDto)
                ResponseEntity.ok(updatedResume)
            } catch (e: Exception) {
                ResponseEntity.badRequest().body(e.message)
            }
        } else if (user == null){
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.badRequest().body("Resume ID is missing in the request.")
        }
    }

    @DeleteMapping("/delete/{userId}")
    fun deleteResume(@PathVariable userId: Int): ResponseEntity<Void> {
        return try {
            resumeService.deleteResumeAndRelatedDetails(userId)
            ResponseEntity.noContent().build()
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }
}
