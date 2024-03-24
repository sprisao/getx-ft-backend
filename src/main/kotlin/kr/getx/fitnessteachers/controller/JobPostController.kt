package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.JobPostDto
import kr.getx.fitnessteachers.service.AuthenticationValidationService
import kr.getx.fitnessteachers.service.JobPostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.security.core.Authentication
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@RestController
@RequestMapping("/api/jobPosts")
class JobPostController(
    private val jobPostService: JobPostService,
    private val authenticationValidationService: AuthenticationValidationService,
) {

    @GetMapping("/all")
    fun getAllJobPosts(): ResponseEntity<List<JobPostDto>> =
        ResponseEntity.ok(jobPostService.findAll().map(JobPostDto::fromEntity))

    // userId로 구인게시판 조회 ( 센터 소유주만 조회 가능 )
    @GetMapping("/owner/{userId}")
    fun getJobPostById(@PathVariable userId: Int, authentication: Authentication): ResponseEntity<List<JobPostDto>> {
        authenticationValidationService.validateRequestingUser(userId, authentication)
        return ResponseEntity.ok(jobPostService.findJobPostsByUserId(userId).map(JobPostDto::fromEntity))
    }

    @GetMapping("/{jobPostId}")
    fun getJobPostById(@PathVariable jobPostId: Int): ResponseEntity<JobPostDto> =
        jobPostService.findById(jobPostId)?.let { jobPost -> ResponseEntity.ok(JobPostDto.fromEntity(jobPost)) }
            ?: ResponseEntity.notFound().build()

    // 타이틀 유사한 구인게시판 조회 ( 차후 추가 작업과 함께 구현 예정 )
    @GetMapping("/{jobPostId}/similar")
    fun getSimilarJobPosts(@PathVariable jobPostId: Int): ResponseEntity<List<JobPostDto>> =
        ResponseEntity.ok(jobPostService.findSimilarJobPosts(jobPostId).map(JobPostDto::fromEntity))

    @PostMapping("/add")
    fun createJobPost(@RequestBody jobPostDto: JobPostDto, authentication: Authentication): ResponseEntity<JobPostDto> {
        val user = authenticationValidationService.getUserFromAuthentication(authentication)
        val center = authenticationValidationService.validateCenterOwnership(jobPostDto.centerId, user)
        val createdJobPost = jobPostService.createJobPost(jobPostDto, center, user)
        return ResponseEntity.ok(JobPostDto.fromEntity(createdJobPost))
    }

    @PutMapping("/update/{jobPostId}")
    fun updateJobPost(@PathVariable jobPostId: Int, @RequestBody jobPostDto: JobPostDto, authentication: Authentication): ResponseEntity<JobPostDto> {
        val user = authenticationValidationService.getUserFromAuthentication(authentication)
        authenticationValidationService.validateJobPostModification(jobPostId, user)
        val updatedJobPost = jobPostService.updateJobPost(jobPostId, jobPostDto, user)
        return ResponseEntity.ok(JobPostDto.fromEntity(updatedJobPost))
    }

    @DeleteMapping("/delete/{jobPostId}")
    fun deleteJobPost(@PathVariable jobPostId: Int, authentication: Authentication): ResponseEntity<Void> {
        val user = authenticationValidationService.getUserFromAuthentication(authentication)
        authenticationValidationService.validateJobPostDeletion(jobPostId, user)
        jobPostService.deleteById(jobPostId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/search")
    fun searchJobPosts(
        @RequestParam(required = false) recruitmentStatus: String?,
        @RequestParam(required = false) jobCategory: String?,
        @RequestParam(required = false) locationProvince: String?,
        @RequestParam(required = false) locationCity: String?,
        pageable: Pageable
    ): ResponseEntity<Page<JobPostDto>> {
        val page = jobPostService.searchJobPosts(recruitmentStatus, jobCategory, locationProvince, locationCity, pageable)
        return ResponseEntity.ok(page.map(JobPostDto::fromEntity))
    }
}
