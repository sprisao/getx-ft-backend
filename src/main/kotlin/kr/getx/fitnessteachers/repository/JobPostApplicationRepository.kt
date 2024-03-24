package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.entity.JobPostApplication
import kr.getx.fitnessteachers.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface JobPostApplicationRepository : JpaRepository<JobPostApplication, Int> {
    fun findByJobPost(jobPost: JobPost): List<JobPostApplication>
    fun findByJobPostJobPostId(jobPostId: Int): List<JobPostApplication>
    fun findByJobPostAndUser(jobPost: JobPost, user: User): JobPostApplication

    fun findByUser(user: User): List<JobPostApplication>
}