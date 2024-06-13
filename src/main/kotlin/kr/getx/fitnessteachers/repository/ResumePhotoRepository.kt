package kr.getx.fitnessteachers.repository

import java.util.*
import kr.getx.fitnessteachers.entity.ResumePhoto
import kr.getx.fitnessteachers.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ResumePhotoRepository : JpaRepository<ResumePhoto, Int> {
    fun findByResumePhotoIdIn(resumePhotoIds: List<Int>): List<ResumePhoto>

    fun findByUser(user: User): List<ResumePhoto>

    // Soft Delete
    fun findAllByResumePhotoIdInAndIsDeletedFalse(resumePhotoIds: List<Int>): List<ResumePhoto>

    // hard deleted
    fun findByIsDeletedTrueAndDeletedAtBefore(expiredDateTime: LocalDateTime): List<ResumePhoto>
}
