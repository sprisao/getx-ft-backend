package kr.getx.fitnessteachers.repository

import java.util.*
import kr.getx.fitnessteachers.entity.JobPostApplication
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface JobPostApplicationRepository : JpaRepository<JobPostApplication, Int> {
    fun findByJobPostJobPostId(jobPostId: Int): List<JobPostApplication>
    fun findByUserUserId(userId: Int): List<JobPostApplication>
    fun countByJobPostJobPostId(jobPostId: Int): Int

    // Soft Delete
    fun findByUserUserIdAndJobPostJobPostIdAndIsDeletedFalse(
            userId: Int,
            jobPostId: Int
    ): JobPostApplication?

    fun findByIsDeletedTrueAndIsDeletedAtBefore(expiredDateTimhe: LocalDateTime): List<JobPostApplication>
}
