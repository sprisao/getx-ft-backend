package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.ResumeAttachment
import java.time.LocalDateTime

data class ResumeAttachmentDto(
    val resumeAttachmentId: Int? = 0,
    val userId: Int,
    val attachmentUrl: String,
    val createdAt: LocalDateTime? = null
)
{
    companion object {
        fun fromEntity(resumeAttachment: ResumeAttachment): ResumeAttachmentDto = ResumeAttachmentDto(
            resumeAttachmentId = resumeAttachment.resumeAttachmentId,
            userId = resumeAttachment.user.userId,
            attachmentUrl = resumeAttachment.attachmentUrl,
            createdAt = resumeAttachment.createdAt
        )
    }
}
