package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.JobPostFavorite
import java.time.LocalDateTime

data class JobPostFavoriteDto(
    val jobPostFavoriteId: Int,
    val jobPostId: Int,
    val userId: Int,
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromEntity(jobPostFavorite: JobPostFavorite): JobPostFavoriteDto {
            return JobPostFavoriteDto(
                jobPostFavoriteId = jobPostFavorite.jobPostFavoriteId,
                jobPostId = jobPostFavorite.jobPost.jobPostId,
                userId = jobPostFavorite.user.userId,
                createdAt = jobPostFavorite.createdAt
            )
        }
    }
}
