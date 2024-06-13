package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.ResumeAttachment
import kr.getx.fitnessteachers.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ResumeAttachmentRepository : JpaRepository<ResumeAttachment, Int> {
    fun findByUser(user: User): List<ResumeAttachment>

    fun findByResumeAttachmentIdIn(resumeAttachmentIds: List<Int>): List<ResumeAttachment>

    // Soft Delete
    fun findAllByResumeAttachmentIdInAndIsDeletedFalse(
            resumeAttachmentIds: List<Int>
    ): List<ResumeAttachment>

    // Hard Delete
    fun findByIsDeletedTrueAndDeletedAtBefore(expiredDateTime: LocalDateTime): List<ResumeAttachment>
}
