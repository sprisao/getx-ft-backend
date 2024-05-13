package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.JobPostApplication
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JobPostApplicationRepository : JpaRepository<JobPostApplication, Int> {
    fun findByJobPostJobPostId(jobPostId: Int): List<JobPostApplication>
    fun findByUserUserId(userId: Int): List<JobPostApplication>
    fun countByJobPostJobPostId(jobPostId: Int): Int

    // Soft Delete
    fun findByUserUserIdAndJobPostJobPostIdAndIsDeletedFalse(userId: Int, jobPostId: Int): Optional<JobPostApplication>

}