package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.JobPostApplication
import org.springframework.data.jpa.repository.JpaRepository

interface JobPostApplicationRepository : JpaRepository<JobPostApplication, Int> {
    fun findByUserAndJobPost(userId: Int, jobPostId: Int): JobPostApplication?
    fun findByJobPostId(jobPostId: Int): List<JobPostApplication>
    fun findByUserId(userId: Int): List<JobPostApplication>
    fun countByJobPostId(jobPostId: Int): Int
}