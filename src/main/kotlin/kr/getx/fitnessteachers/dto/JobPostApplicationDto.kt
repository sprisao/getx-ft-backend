package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.JobPostApplication
import java.time.LocalDateTime

data class JobPostApplicationDto(
    val jobPostApplicationId: Int,
    val jobPostId: Int,
    val userId: Int,
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromEntity(entity: JobPostApplication): JobPostApplicationDto {
            return JobPostApplicationDto(
                jobPostApplicationId = entity.jobPostApplicationId,
                jobPostId = entity.jobPost.jobPostId,
                userId = entity.user.userId,
                createdAt = entity.createdAt
            )
        }
    }
}
