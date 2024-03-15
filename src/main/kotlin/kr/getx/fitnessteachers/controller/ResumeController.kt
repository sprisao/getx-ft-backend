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
import kr.getx.fitnessteachers.exceptions.ResumeNotFoundException
import kr.getx.fitnessteachers.exceptions.UserNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@RestController
@RequestMapping("/api/resumes")
class ResumeController(
    private val resumeService: ResumeService,
    private val userService: UserService,
    private val experienceService: ExperienceService,
    private val certificationService: CertificationService,
    private val educationService: EducationService
) {

    @GetMapping("/all")
    fun getAllResumes(): List<Resume> = resumeService.getAllResumes()

    @PostMapping("/add")
    fun addResume(@RequestBody resumeDto: ResumeDto, request: HttpServletRequest): ResponseEntity<Resume> {
        val saveResume = resumeService.addResumeWithDetails(resumeDto)
        return ResponseEntity.ok(saveResume)
    }

    @GetMapping("/{userId}")
    fun getResumeByUserId(@PathVariable userId: Int): ResponseEntity<Any> {
        val user = userService.findUserById(userId)
            ?: throw UserNotFoundException(userId)
        val resume = resumeService.getResumeByUserId(user.userId)
            ?: throw ResumeNotFoundException(user.userId)
        val resumeDto = ResumeDto(
            resumeId = resume.resumeId,
            userId = user.userId,
            photos = StringConversionUtils.convertStringToList(resume.photos ?: " "),
            createdAt = resume.createdAt,
            experiences = experienceService.getExperienceByResumeId(resume.resumeId)
                .map { ExperienceDto(it.experienceId, it.description, it.startDate, it.endDate, it.createdAt) },
            educations = educationService.getEducationByResumeId(resume.resumeId)
                .map { EducationDto(it.educationId, it.courseName, it.institution, it.completionDate, it.createdAt) },
            certifications = certificationService.getCertificationByResumeId(resume.resumeId)
                .map { CertificationDto(it.certificationId, it.name, it.issuedBy, it.issuedDate, it.createdAt) }
        )
        return ResponseEntity.ok().body(resumeDto)
    }

    @PutMapping("/update/{userId}")
    fun updateResumeByUserId(@PathVariable userId: Int, @RequestBody resumeDto: ResumeDto): ResponseEntity<Any> {
        val user = userService.findUserById(userId)
            ?: throw UserNotFoundException(userId)

        // 이력서 업데이트를 위한 데이터 검증 및 설정
        resumeDto.userId = userId

        try {
            val updatedResume = resumeService.updateResumeWithDetails(resumeDto)
            return ResponseEntity.ok(updatedResume)
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body(e.message)
        }
    }

    @DeleteMapping("/delete/{userId}")
    fun deleteResume(@PathVariable userId: Int): ResponseEntity<Any> {
        userService.findUserById(userId) ?: throw UserNotFoundException(userId)
        return try {
            resumeService.deleteResumeAndRelatedDetails(userId)
            ResponseEntity.ok().body("이력서 삭제를 완료했습니다. 유저 ID : $userId")
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(e.message)
        }
    }
}
