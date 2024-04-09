package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.JobPostDto
import kr.getx.fitnessteachers.exceptions.CenterNotFoundException
import kr.getx.fitnessteachers.exceptions.UserNotFoundException
import kr.getx.fitnessteachers.service.CenterService
import kr.getx.fitnessteachers.service.JobPostService
import kr.getx.fitnessteachers.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@RestController
@RequestMapping("/api/jobPosts")
class JobPostController(
    private val jobPostService: JobPostService,
    private val userService: UserService,
    private val centerService: CenterService,
) {

    @GetMapping("/all")
    fun getAllJobPosts(): ResponseEntity<List<JobPostDto>> =
        ResponseEntity.ok(jobPostService.findAll().map(JobPostDto::fromEntity))

    @PostMapping("/add")
    fun createJobPost(@RequestBody jobPostDto: JobPostDto): ResponseEntity<JobPostDto> {
        val center = centerService.findById(jobPostDto.center.centerId) ?: throw CenterNotFoundException(jobPostDto.center.centerId)
        val user = userService.findUserById(center.user.userId) ?: throw UserNotFoundException(center.user.userId)
        val createdJobPost = jobPostService.createJobPost(jobPostDto, center, user)
        return ResponseEntity.ok(JobPostDto.fromEntity(createdJobPost))
    }

    @GetMapping("/owner/{userId}")
    fun getJobPostByUserId(@PathVariable userId: Int): ResponseEntity<List<JobPostDto>> {
        return ResponseEntity.ok(jobPostService.findJobPostsByUserId(userId).map(JobPostDto::fromEntity))
    }

    @PutMapping("/update/{jobPostId}")
    fun updateJobPost(@PathVariable jobPostId: Int, @RequestBody jobPostDto: JobPostDto): ResponseEntity<JobPostDto> {
        val center = centerService.findById(jobPostDto.center.centerId) ?: throw CenterNotFoundException(jobPostDto.center.centerId)
        val user = userService.findUserById(center.user.userId) ?: throw UserNotFoundException(center.user.userId)
        val updatedJobPost = jobPostService.updateJobPost(jobPostId, jobPostDto, user)
        return ResponseEntity.ok(JobPostDto.fromEntity(updatedJobPost))
    }

    @DeleteMapping("/delete/{jobPostId}")
    fun deleteJobPost(@PathVariable jobPostId: Int): ResponseEntity<Void> {
        jobPostService.deleteById(jobPostId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{jobPostId}")
    fun getJobPostById(@PathVariable jobPostId: Int): ResponseEntity<JobPostDto> =
        jobPostService.findById(jobPostId)?.let { jobPost -> ResponseEntity.ok(JobPostDto.fromEntity(jobPost)) }
            ?: ResponseEntity.notFound().build()

    // 타이틀 유사한 구인게시판 조회 ( 차후 추가 작업과 함께 구현 예정 )
    @GetMapping("/{jobPostId}/similar")
    fun getSimilarJobPosts(@PathVariable jobPostId: Int): ResponseEntity<List<JobPostDto>> =
        ResponseEntity.ok(jobPostService.findSimilarJobPosts(jobPostId))

    @GetMapping("/search")
    fun searchJobPosts(
        @RequestParam(required = false) isRecruitmentOpen: Boolean?, // 채용 상태
        @RequestParam(required = false) jobCategory: List<String>?, // 직업 카테고리
        @RequestParam(required = false) employmentType: String?, // 시급, 월급
        @RequestParam(required = false) hasBaseSalary: Boolean?, // 기본급 유무
        @RequestParam(required = false) experienceLevel: Int?, // 경력  N년 이상 관련 요건
        @RequestParam(required = false) sidoEnglish: String?, // 시/도 영어
        @RequestParam(required = false) sigunguEnglish: String?, // 시/군/구 영어
        pageable: Pageable
    ): ResponseEntity<Page<JobPostDto>> {
        val decodedJobCategories = jobCategory?.map { URLDecoder.decode(it, StandardCharsets.UTF_8.name()) }
        val page = jobPostService.searchJobPosts(
            isRecruitmentOpen,
            decodedJobCategories,
            employmentType,
            hasBaseSalary,
            experienceLevel,
            sidoEnglish,
            sigunguEnglish,
            pageable)
        return ResponseEntity.ok(page.map(JobPostDto::fromEntity))
    }

    // 단축 공유 URL 생성 API
    @GetMapping("/{jobPostId}/shortUrl")
    fun generateShortUrl(@PathVariable jobPostId:Int): ResponseEntity<String> {
        val jobPost = jobPostService.findById(jobPostId) ?: return ResponseEntity.notFound().build()
        val shortUrl = jobPostService.generateShortUrl(jobPost)
        return ResponseEntity.ok(shortUrl)
    }
}
