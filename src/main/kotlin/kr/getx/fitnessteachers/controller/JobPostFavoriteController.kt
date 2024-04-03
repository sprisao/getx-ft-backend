package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.service.JobPostFavoriteService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import kr.getx.fitnessteachers.dto.JobPostFavoriteDto
import kr.getx.fitnessteachers.dto.UserDto
import kr.getx.fitnessteachers.dto.JobPostDto

@RestController
@RequestMapping("/api/jobPostFavorites")
class JobPostFavoriteController (
    private val jobPostFavoriteService: JobPostFavoriteService
) {
    // 구직 공고 즐겨찾기 등록
    @PostMapping("/{jobPostId}/{userId}")
    fun addFavorite(@PathVariable jobPostId: Int, @PathVariable userId: Int): ResponseEntity<JobPostFavoriteDto> {
        val favorite = jobPostFavoriteService.addFavorite(userId, jobPostId)
        return ResponseEntity.ok(JobPostFavoriteDto.fromEntity(favorite))
    }

    // 구직 공고 즐겨찾기 취소
    @DeleteMapping("/{jobPostId}/{userId}")
    fun cancelFavorite(@PathVariable jobPostId: Int, @PathVariable userId: Int): ResponseEntity<Void> {
        jobPostFavoriteService.cancelFavorite(userId, jobPostId)
        return ResponseEntity.ok().build<Void>()
    }

    // 즐겨찾기한 구직 공고들 조회
    @GetMapping("/{userId}/favoritedJobPosts")
    fun getFavoritedJobPosts(@PathVariable userId: Int): ResponseEntity<List<JobPostDto>> {
        val jobPosts = jobPostFavoriteService.getFavoritedJobPosts(userId)
        return ResponseEntity.ok(jobPosts)
    }

    // 구직 공고 즐겨찾기한 사용자들 조회
    @GetMapping("/{jobPostId}/favoritedUsers")
    fun getFavoritedUsers(@PathVariable jobPostId: Int): ResponseEntity<List<UserDto>> {
        val users = jobPostFavoriteService.getFavoritedUsers(jobPostId)
        return ResponseEntity.ok(users)
    }

    // 구직 공고 즐겨찾기한 사용자들 수 조회
    @GetMapping("/{jobPostId}/favoritedUserCount")
    fun getFavoritedUserCount(@PathVariable jobPostId: Int): ResponseEntity<Int> {
        val count = jobPostFavoriteService.getFavoritedUserCount(jobPostId)
        return ResponseEntity.ok(count)
    }
}