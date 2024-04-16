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

        fun findResumeAttachmentById(resumeAttachmentId: Int): ResumeAttachment {
            return resumeAttachmentRepository.findById(resumeAttachmentId).orElseThrow {
                IllegalArgumentException("해당 첨부파일을 찾을수 없습니다 !! resumeAttachmentId : $resumeAttachmentId")
            }
        }

        fun addResumeAttachment(resumeAttachmentDto: ResumeAttachmentDto): ResumeAttachment {
            val user = userRepository.findById(resumeAttachmentDto.userId).orElseThrow {
                IllegalArgumentException("해당 유저를 찾을수 없습니다 !! userId : ${resumeAttachmentDto.userId}")
            }
            return resumeAttachmentRepository.save(
                ResumeAttachment(
                    resumeAttachmentId = resumeAttachmentDto.resumeAttachmentId ?: 0,
                    user = user,
                    attachmentUrl = resumeAttachmentDto.attachmentUrl,
                    createdAt = resumeAttachmentDto.createdAt ?: LocalDateTime.now()
                )
            )
        }

        fun updateResumeAttachment(resumeAttachmentId: Int, resumeAttachmentDto: ResumeAttachmentDto): ResumeAttachment {
            val resumeAttachment = resumeAttachmentRepository.findById(resumeAttachmentId).orElseThrow {
                IllegalArgumentException("해당 첨부파일을 찾을수 없습니다 !! resumeAttachmentId : $resumeAttachmentId")
            }
            resumeAttachment.attachmentUrl = resumeAttachmentDto.attachmentUrl
            return resumeAttachmentRepository.save(resumeAttachment)
        }

        fun deleteResumeAttachment(resumeAttachmentId: Int) {
            resumeAttachmentRepository.deleteById(resumeAttachmentId)
        }
}