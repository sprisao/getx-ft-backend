package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.JobPostDto
import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.service.AuthenticationValidationService
import kr.getx.fitnessteachers.service.CenterService
import kr.getx.fitnessteachers.service.JobPostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.security.core.Authentication
import java.nio.file.AccessDeniedException
@RestController
@RequestMapping("/api/jobPosts")
class JobPostController(
    private val jobPostService: JobPostService,
    private val centerService: CenterService,
    private val authenticationValidationService: AuthenticationValidationService,
) {

    @GetMapping
    fun getAllJobPosts(): ResponseEntity<List<JobPost>> =
        ResponseEntity.ok(jobPostService.findAll())

    @GetMapping("/{userId}")
    fun getJobPostById(@PathVariable userId: Int, authentication: Authentication): ResponseEntity<List<JobPost>> {
        val requestingUser = authenticationValidationService.getUserFromAuthentication(authentication)

        if (requestingUser.userId != userId) {
            return ResponseEntity.ok().body(emptyList())
        }

        val centers = centerService.getCenterByUserId(userId)
        if (centers.isEmpty()) {
            return ResponseEntity.ok().body(emptyList())
        }

        val jobPosts = centers.map { center ->
            jobPostService.findByCenterId(center.centerId)
        }.flatten()


        return ResponseEntity.ok(jobPosts)
    }

    @PostMapping("/add")
    fun createJobPost(@RequestBody jobPostDto: JobPostDto, authentication: Authentication): ResponseEntity<Any> {
        // SecurityContext 에서 인증 정보 가져오기
        val user = authenticationValidationService.getUserFromAuthentication(authentication)
        // 센터 소유주인지 확인
        val center = authenticationValidationService.validateCenterOwnership(jobPostDto.centerId, user)

        val jobPost = JobPost(
            center = center,
            recruitmentStatus = jobPostDto.recruitmentStatus,
            responsibilities = jobPostDto.responsibilities,
            workLocation = jobPostDto.workLocation,
            workHours = jobPostDto.workHours,
            workDays = jobPostDto.workDays,
            employmentType = jobPostDto.employmentType,
            numberOfPositions = jobPostDto.numberOfPositions,
            salary = jobPostDto.salary,
            qualifications = jobPostDto.qualifications,
            applicationPeriodStart = jobPostDto.applicationPeriodStart,
            applicationPeriodEnd = jobPostDto.applicationPeriodEnd,
            contactEmail = jobPostDto.contactEmail,
            contactPhone = jobPostDto.contactPhone,
            contactPerson = jobPostDto.contactPerson,
            details = jobPostDto.details
        )
        val saveJobPost = jobPostService.save(jobPost)
        return ResponseEntity.ok(saveJobPost)
    }

    @PutMapping("/update/{jobPostId}")
    fun updateJobPost(@PathVariable jobPostId: Int, @RequestBody jobPostDto: JobPostDto, authentication: Authentication): ResponseEntity<Any> {
        val user = authenticationValidationService.getUserFromAuthentication(authentication)
        val existingJobPost = jobPostService.findById(jobPostId)
            ?: return ResponseEntity.ok().body("해당 구인게시판이 존재하지 않습니다.")

        return if (existingJobPost != null) {
            val centerId = existingJobPost.center?.centerId
                ?: return ResponseEntity.ok().body("해당 구인게시글에 연결된 센터가 존재하지 않습니다.")

            try {
                authenticationValidationService.validateCenterOwnership(centerId, user)
                val updateJobPost = jobPostService.updateJobPost(existingJobPost, jobPostDto)
                ResponseEntity.ok(updateJobPost)
            } catch (e: AccessDeniedException) {
                ResponseEntity.ok().body("해당 구인게시판이 존재하지 않거나 권한이 없습니다.")
            }
        } else {
            ResponseEntity.ok().body("해당 구인게시판이 존재하지 않거나 권한이 없습니다.")
        }
    }

    @DeleteMapping("/delete/{jobPostId}")
    fun deleteJobPost(@PathVariable jobPostId: Int, authentication: Authentication): ResponseEntity<Any> {
        val user = authenticationValidationService.getUserFromAuthentication(authentication)
        val jobPost = jobPostService.findById(jobPostId)
            ?: return ResponseEntity.ok().body("해당 구인게시판이 존재하지 않습니다.")

        if (jobPost != null) {
            // validateCenterOwnership 메서드가 Center 객체를 반환하거나 예외를 던집니다.
            // 예외가 던져지지 않았다면, 삭제 권한이 있는 것으로 간주합니다.
            authenticationValidationService.validateCenterOwnership(jobPost.center?.centerId ?: 0, user)
            jobPostService.deleteById(jobPostId)
            return ResponseEntity.ok().body("해당 구인게시판이 삭제되었습니다.")
        } else {
            return ResponseEntity.ok().body("해당 구인게시판이 존재하지 않거나 권한이 없습니다.")
        }
    }
}
