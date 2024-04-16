package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.ResumeAttachmentDto
import kr.getx.fitnessteachers.entity.ResumeAttachment
import kr.getx.fitnessteachers.repository.ResumeAttachmentRepository
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ResumeAttachmentService(
    private val resumeAttachmentRepository: ResumeAttachmentRepository,
    private val userRepository: UserRepository,
) {

    fun getAllResumeAttachments(): List<ResumeAttachment> = resumeAttachmentRepository.findAll()

    fun findResumeAttachmentByUserIds(userId: Int): List<ResumeAttachment> {
        val user = userRepository.findById(userId).orElseThrow {
            java.lang.IllegalArgumentException("해당 유저를 찾을수 없습니다 !! userId : $userId")
        }
        return resumeAttachmentRepository.findByUser(user)
    }

    fun addResumeAttachment(resumeAttachmentDto: List<ResumeAttachmentDto>): List<ResumeAttachment> {
        return resumeAttachmentDto.map { dto ->
            val user = userRepository.findById(dto.userId).orElseThrow {
                IllegalArgumentException("해당 유저를 찾을수 없습니다 !! userId : ${dto.userId}")
            }
            resumeAttachmentRepository.save(
                ResumeAttachment(
                    resumeAttachmentId = dto.resumeAttachmentId ?: 0,
                    user = user,
                    attachmentUrl = dto.attachmentUrl,
                    createdAt = dto.createdAt ?: LocalDateTime.now()
                )
            )
        }
    }

    fun updateResumeAttachment(resumeAttachmentDto: List<ResumeAttachmentDto>): List<ResumeAttachment> {
        val resumeAttachmentIds = resumeAttachmentDto.mapNotNull { it.resumeAttachmentId }
        val existingResumeAttachments = resumeAttachmentRepository.findByResumeAttachmentIdIn(resumeAttachmentIds)
            .associateBy { it.resumeAttachmentId }

        return resumeAttachmentDto.mapNotNull { dto ->
            val resumeAttachment = existingResumeAttachments[dto.resumeAttachmentId]?.apply {
                attachmentUrl = dto.attachmentUrl
            } ?: return@mapNotNull null
            resumeAttachmentRepository.save(resumeAttachment)
        }
    }

    fun deleteResumeAttachment(resumeAttachmentId: List<Int>) {
        resumeAttachmentRepository.deleteAllById(resumeAttachmentId)
    }

    fun findResumeAttachmentById(resumeAttachmentIds: List<Int>): List<ResumeAttachment> =
        resumeAttachmentRepository.findByResumeAttachmentIdIn(resumeAttachmentIds)
}