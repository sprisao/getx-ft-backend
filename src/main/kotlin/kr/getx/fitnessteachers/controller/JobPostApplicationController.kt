package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.JobPostApplicationDto
import kr.getx.fitnessteachers.dto.JobPostDto
import kr.getx.fitnessteachers.dto.ResumeDto
import kr.getx.fitnessteachers.service.JobPostApplicationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/jobPostApplications")
class JobPostApplicationController (
    private val jobPostApplicationService: JobPostApplicationService
) {
    // 강사 -> 구직 공고 지원
    // 구직 공고 지원 등록
    @PostMapping("/{jobPostId}/{userId}")
    fun applyToJobPost(@PathVariable jobPostId: Int, @PathVariable userId: Int): ResponseEntity<JobPostApplicationDto> {
        val application = jobPostApplicationService.applyToJobPost(userId, jobPostId)
        return ResponseEntity.ok(JobPostApplicationDto.fromEntity(application))
    }

    // 강사 - > 구직 공고 지원 취소
    // 구직 공고 지원 취소
    @DeleteMapping("/{jobPostId}/{userId}")
    fun cancelApplication(@PathVariable jobPostId: Int, @PathVariable userId: Int): ResponseEntity<Void> {
        jobPostApplicationService.cancelApplication(userId, jobPostId)
        return ResponseEntity.ok().build<Void>()
    }

    // 오너 -> 지원한 지원자들의 수
    // 구직 공고 지원자 수 조회
    @GetMapping("/{jobPostId}/applicantCount")
    fun getApplicantCount(@PathVariable jobPostId: Int): ResponseEntity<Int> {
        val count = jobPostApplicationService.getApplicantCount(jobPostId)
        return ResponseEntity.ok(count)
    }

    // 오너 -> 지원한 지원자들의 이력서들
    // 구직 공고 지원자 이력서들 전부 조회
    @GetMapping("/{jobPostId}/applicantResumes")
    fun getApplicantResumes(@PathVariable jobPostId: Int): ResponseEntity<List<ResumeDto>> {
        val resumes = jobPostApplicationService.getApplicantResumes(jobPostId)
        return ResponseEntity.ok(resumes)
    }

    // 지원자 -> 지원한 구직 공고들
    // 지원자가 지원한 구직 공고들 조회
    @GetMapping("/{userId}/appliedJobPosts")
    fun getAppliedJobPosts(@PathVariable userId: Int): ResponseEntity<List<JobPostDto>> {
        val jobPosts = jobPostApplicationService.getAppliedJobPosts(userId)
        return ResponseEntity.ok(jobPosts)
    }
}