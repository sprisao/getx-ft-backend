package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.JobPostDto
import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.exceptions.CenterIdNotFoundExceptionByJobPost
import kr.getx.fitnessteachers.exceptions.CenterNotFoundException
import kr.getx.fitnessteachers.exceptions.JobPostNotFoundException
import kr.getx.fitnessteachers.service.AuthenticationValidationService
import kr.getx.fitnessteachers.service.CenterService
import kr.getx.fitnessteachers.service.JobPostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.security.core.Authentication
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
            throw CenterNotFoundException(userId)
        }

        val centers = centerService.getCenterByUserId(userId)
        if (centers.isEmpty()) {
            throw CenterNotFoundException(userId)
        }

        val jobPosts = centers.flatMap { center->
            jobPostService.findByCenterId(center.centerId)
        }

        return ResponseEntity.ok(jobPosts)
    }

    @PostMapping("/add")
    fun createJobPost(@RequestBody jobPostDto: JobPostDto, authentication: Authentication): ResponseEntity<Any> {
        // SecurityContext 에서 인증 정보 가져오기
        val user = authenticationValidationService.getUserFromAuthentication(authentication)
        // 센터 소유주인지 확인
        val center = centerService.findById(jobPostDto.centerId)
            ?: throw CenterNotFoundException(jobPostDto.centerId)

        authenticationValidationService.validateCenterOwnership(center.centerId, user)

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
        return ResponseEntity.ok(jobPostService.save(jobPost))
    }

    @PutMapping("/update/{jobPostId}")
    fun updateJobPost(@PathVariable jobPostId: Int, @RequestBody jobPostDto: JobPostDto, authentication: Authentication): ResponseEntity<Any> {
        val user = authenticationValidationService.getUserFromAuthentication(authentication)
        val existingJobPost = jobPostService.findById(jobPostId)
            ?: throw JobPostNotFoundException(jobPostId)

        val centerId = existingJobPost.center.centerId
            ?: throw CenterIdNotFoundExceptionByJobPost("Center ID not found by job post ID: $jobPostId")

        authenticationValidationService.validateCenterOwnership(centerId, user)

        return ResponseEntity.ok(jobPostService.updateJobPost(existingJobPost, jobPostDto))
    }

    @DeleteMapping("/delete/{jobPostId}")
    fun deleteJobPost(@PathVariable jobPostId: Int, authentication: Authentication): ResponseEntity<Any> {
        val user = authenticationValidationService.getUserFromAuthentication(authentication)
        val jobPost = jobPostService.findById(jobPostId)
            ?: throw JobPostNotFoundException(jobPostId)

        // jobPost.center.centerId 가 null 이 아닌지 확인합니다.
        val centerId = jobPost.center.centerId
            ?: throw CenterIdNotFoundExceptionByJobPost("Center ID not found by job post ID: $jobPostId")

        // validateCenterOwnership 메서드가 예외를 던지지 않으면 삭제 권한이 있는 것으로 간주합니다.
        authenticationValidationService.validateCenterOwnership(centerId, user)

        jobPostService.deleteById(jobPostId)
        return ResponseEntity.ok().body("해당 구인게시판이 삭제되었습니다.")
    }
}
