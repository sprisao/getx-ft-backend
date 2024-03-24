package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.JobPostDto
import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.exceptions.CenterNotFoundException
import kr.getx.fitnessteachers.exceptions.JobPostNotFoundException
import kr.getx.fitnessteachers.service.AuthenticationValidationService
import kr.getx.fitnessteachers.service.CenterService
import kr.getx.fitnessteachers.service.JobPostService
import kr.getx.fitnessteachers.service.ResumeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.security.core.Authentication
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@RestController
@RequestMapping("/api/jobPosts")
class JobPostController(
    private val jobPostService: JobPostService,
    private val centerService: CenterService,
    private val authenticationValidationService: AuthenticationValidationService,
) {

    @GetMapping("/all")
    fun getAllJobPosts(): ResponseEntity<List<JobPost>> =
        ResponseEntity.ok(jobPostService.findAll())

    // userId로 구인게시판 조회 ( 센터 소유주만 조회 가능 )
    @GetMapping("/owner/{userId}")
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

    @GetMapping("/{jobPostId}")
    fun getJobPostById(@PathVariable jobPostId: Int): ResponseEntity<JobPost> {
        val jobPost = jobPostService.findById(jobPostId)
            ?: throw JobPostNotFoundException(jobPostId)

        return ResponseEntity.ok(jobPost)
    }

    // 타이틀 유사한 구인게시판 조회 ( 차후 추가 작업과 함께 구현 예정 )
    @GetMapping("/{jobPostId}/similar")
    fun getSimilarJobPosts(@PathVariable jobPostId: Int): ResponseEntity<List<JobPost>> {
        val similarJobPosts = jobPostService.findSimilarJobPosts(jobPostId)
        return ResponseEntity.ok(similarJobPosts)
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
            isDisplayAlready = jobPostDto.isDisplayAlready,
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
            title = jobPostDto.title,
            details = jobPostDto.details,
            jobCategory = jobPostDto.jobCategory
        )
        return ResponseEntity.ok(jobPostService.save(jobPost))
    }

    @PutMapping("/update/{jobPostId}")
    fun updateJobPost(@PathVariable jobPostId: Int, @RequestBody jobPostDto: JobPostDto, authentication: Authentication): ResponseEntity<Any> {
        val user = authenticationValidationService.getUserFromAuthentication(authentication)
        val existingJobPost = jobPostService.findById(jobPostId)
            ?: throw JobPostNotFoundException(jobPostId)

        val centerId = existingJobPost.center.centerId

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

        // validateCenterOwnership 메서드가 예외를 던지지 않으면 삭제 권한이 있는 것으로 간주합니다.
        authenticationValidationService.validateCenterOwnership(centerId, user)

        jobPostService.deleteById(jobPostId)
        return ResponseEntity.ok().body("해당 구인게시판이 삭제되었습니다.")
    }

    // recruitmentStatus, jobCategory, 연결된 center의 locationProvince, locationCity 로 필터 검색 기능
    @GetMapping("/search")
    fun searchJobPosts(
        @RequestParam(required = false) recruitmentStatus: String?,
        @RequestParam(required = false) jobCategory: String?,
        @RequestParam(required = false) locationProvince: String?,
        @RequestParam(required = false) locationCity: String?,
        pageable: Pageable
    ): ResponseEntity<Page<JobPostDto>> {
        val decodedRecruitmentStatus = recruitmentStatus?.let { URLDecoder.decode(it, StandardCharsets.UTF_8.toString()) }
        val decodedJobCategory = jobCategory?.let { URLDecoder.decode(it, StandardCharsets.UTF_8.toString()) }
        val decodedLocationProvince = locationProvince?.let { URLDecoder.decode(it, StandardCharsets.UTF_8.toString()) }
        val decodedLocationCity = locationCity?.let { URLDecoder.decode(it, StandardCharsets.UTF_8.toString()) }

        val page = jobPostService.searchJobPosts(decodedRecruitmentStatus, decodedJobCategory, decodedLocationProvince, decodedLocationCity, pageable)
        val pageDto = page.map { jobPost ->
            JobPostDto(
                jobPostId = jobPost.jobPostId,
                centerId = jobPost.center.centerId,
                isDisplayAlready = jobPost.isDisplayAlready,
                recruitmentStatus = jobPost.recruitmentStatus,
                responsibilities = jobPost.responsibilities,
                workLocation = jobPost.workLocation,
                workHours = jobPost.workHours,
                workDays = jobPost.workDays,
                employmentType = jobPost.employmentType,
                numberOfPositions = jobPost.numberOfPositions,
                salary = jobPost.salary,
                qualifications = jobPost.qualifications,
                applicationPeriodStart = jobPost.applicationPeriodStart,
                applicationPeriodEnd = jobPost.applicationPeriodEnd,
                contactEmail = jobPost.contactEmail,
                contactPhone = jobPost.contactPhone,
                contactPerson = jobPost.contactPerson,
                title = jobPost.title,
                details = jobPost.details,
                jobCategory = jobPost.jobCategory
            )
        }
        return ResponseEntity.ok().body(pageDto)
    }
}
