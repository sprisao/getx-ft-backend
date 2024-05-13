package kr.getx.fitnessteachers.repository

import org.springframework.stereotype.Repository
import kr.getx.fitnessteachers.entity.ResumePhoto
import kr.getx.fitnessteachers.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface ResumePhotoRepository : JpaRepository<ResumePhoto, Int> {
    fun findByResumePhotoIdIn(resumePhotoIds: List<Int>): List<ResumePhoto>

    fun findByUser(user: User): List<ResumePhoto>

    // Soft Delete
    fun findAllByResumePhotoIdAndIsDeletedFalse(resumePhotoIds: List<Int>): Optional<List<ResumePhoto>>
}