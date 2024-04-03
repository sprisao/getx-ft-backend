package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.JobPostFavorite
import org.springframework.data.jpa.repository.JpaRepository
interface JobPostFavoriteRepository : JpaRepository<JobPostFavorite, Int> {
    fun findByUserUserIdAndJobPostJobPostId(userId: Int, jobPostId: Int): JobPostFavorite

    fun findByUserUserId(userId: Int): List<JobPostFavorite>

    fun findByJobPostJobPostId(jobPostId: Int): List<JobPostFavorite>

    fun countByJobPostJobPostId(jobPostId: Int): Int
}