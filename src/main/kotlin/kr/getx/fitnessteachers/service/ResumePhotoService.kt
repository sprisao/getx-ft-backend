package kr.getx.fitnessteachers.service

import org.springframework.stereotype.Service
import kr.getx.fitnessteachers.repository.ResumePhotoRepository
import kr.getx.fitnessteachers.entity.ResumePhoto
import java.time.LocalDateTime
import kr.getx.fitnessteachers.dto.ResumePhotoDto
import kr.getx.fitnessteachers.repository.UserRepository

@Service
class ResumePhotoService(
    private val resumePhotoRepsoitory: ResumePhotoRepository,
    private val userRepository: UserRepository
) {
   fun getAllResumePhotos(): List<ResumePhoto> = resumePhotoRepsoitory.findAll()

    fun findResumePhotosByUserIds(userId: Int): List<ResumePhoto> {
         val user = userRepository.findById(userId).orElseThrow {
              IllegalArgumentException("해당 유저를 찾을수 없습니다 !! userId : $userId")
         }
         return resumePhotoRepsoitory.findByUser(user)
    }

    fun addResumePhotos(resumePhotoDtos: List<ResumePhotoDto>): List<ResumePhoto> {
        return resumePhotoDtos.map { dto ->
            val user = userRepository.findById(dto.userId).orElseThrow {
                IllegalArgumentException("해당 유저를 찾을수 없습니다 !! userId : ${dto.userId}")
            }
            resumePhotoRepsoitory.save(
                ResumePhoto(
                    resumePhotoId = dto.resumePhotoId ?: 0,
                    user = user,
                    photoUrl = dto.photoUrl,
                    createdAt = dto.createdAt ?: LocalDateTime.now()
                )
            )
        }
    }

    fun updateResumePhotos(resumePhotoDtos: List<ResumePhotoDto>): List<ResumePhoto> {
        val resumePhotoIds = resumePhotoDtos.mapNotNull { it.resumePhotoId }
        val existingResumePhotos = resumePhotoRepsoitory.findByResumePhotoIdIn(resumePhotoIds).associateBy { it.resumePhotoId }

        return resumePhotoDtos.mapNotNull { dto ->
            val resumePhoto = existingResumePhotos[dto.resumePhotoId]?.apply {
                photoUrl = dto.photoUrl
            } ?: return@mapNotNull null
            resumePhotoRepsoitory.save(resumePhoto)
        }
    }

    fun deleteResumePhotos(resumePhotoIds: List<Int>) {
        resumePhotoRepsoitory.deleteAllById(resumePhotoIds)
    }

    fun findResumePhotosByIds(resumePhotoIds: List<Int>): List<ResumePhoto> = resumePhotoRepsoitory.findByResumePhotoIdIn(resumePhotoIds)
}