package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.ResumePhoto
import java.time.LocalDateTime

data class ResumePhotoDto(
    val resumePhotoId: Int? = 0,
    val userId: Int,
    val photoUrl: String,
    val createdAt: LocalDateTime? = null,
    val isDeleted: Boolean,
    val isDeletedAt: LocalDateTime?
)
{
    companion object {
        fun fromEntity(resumePhoto: ResumePhoto): ResumePhotoDto = ResumePhotoDto(
            resumePhotoId = resumePhoto.resumePhotoId,
            userId = resumePhoto.user.userId,
            photoUrl = resumePhoto.photoUrl,
            createdAt = resumePhoto.createdAt,
            isDeleted = resumePhoto.isDeleted,
            isDeletedAt = resumePhoto.isDeletedAt
        )
    }
}
