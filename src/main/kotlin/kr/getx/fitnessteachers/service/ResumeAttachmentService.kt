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

    fun syncResumeAttachment(resumeAttachmentDto: List<ResumeAttachmentDto>): List<ResumeAttachment> {
        val existingResumeAttachments = resumeAttachmentRepository.findAll().associateBy { it.resumeAttachmentId }
        val resumeAttachmentIdsToKeep = mutableSetOf<Int>()
        val syncedResumeAttachments = mutableListOf<ResumeAttachment>()

        resumeAttachmentDto.forEach { dto ->
            if (dto.resumeAttachmentId == 0) { // id 값이 0일때 추가
                // 새로운 데이터 추가
                val user = userRepository.findById(dto.userId).orElseThrow {
                    IllegalArgumentException("해당 유저를 찾을 수 없습니다 !! userId : ${dto.userId}")
                }
                val newResumeAttachment = ResumeAttachment(
                    user = user,
                    attachmentUrl = dto.attachmentUrl,
                    createdAt = LocalDateTime.now()
                )
                val savedResumeAttachment = resumeAttachmentRepository.save(newResumeAttachment)
                syncedResumeAttachments.add(savedResumeAttachment)
                resumeAttachmentIdsToKeep.add(savedResumeAttachment.resumeAttachmentId)
            } else {
                // 기존 데이터 업데이트
                val resumeAttachment = existingResumeAttachments[dto.resumeAttachmentId]?.apply {
                    attachmentUrl = dto.attachmentUrl
                }
                if(resumeAttachment != null) {
                    syncedResumeAttachments.add(resumeAttachmentRepository.save(resumeAttachment))
                    resumeAttachmentIdsToKeep.add(resumeAttachment.resumeAttachmentId)
                }
            }
        }

        // 요청에 포함되지 않은 데이터 삭제
        val resumeAttachmentIdsToDelete = existingResumeAttachments.keys - resumeAttachmentIdsToKeep
        if(resumeAttachmentIdsToDelete.isNotEmpty()) {
            resumeAttachmentRepository.deleteAllById(resumeAttachmentIdsToDelete)
        }

        return syncedResumeAttachments
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
        val resumeAttachments = resumeAttachmentRepository.findAllByResumeAttachmentIdAndIsDeletedFalse(resumeAttachmentId).orElseThrow()
        resumeAttachments.forEach {
            it.isDeleted = true
            it.isDeletedAt = LocalDateTime.now()
        }
        resumeAttachmentRepository.saveAll(resumeAttachments)
    }

    fun findResumeAttachmentById(resumeAttachmentIds: List<Int>): List<ResumeAttachment> =
        resumeAttachmentRepository.findByResumeAttachmentIdIn(resumeAttachmentIds)
}