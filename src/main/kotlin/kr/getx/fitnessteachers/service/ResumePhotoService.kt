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

    fun syncResumePhotos(resumePhotoDtos: List<ResumePhotoDto>): List<ResumePhoto> {
        val existingResumePhotos = resumePhotoRepsoitory.findAll().associateBy { it.resumePhotoId }
        val resumePhotoIdsToKeep = mutableSetOf<Int>()
        val syncedResumePhotos = mutableListOf<ResumePhoto>()

        resumePhotoDtos.forEach { dto ->
            if (dto.resumePhotoId == null) {
                // 새로운 데이터 추가
                val user = userRepository.findById(dto.userId).orElseThrow {
                    IllegalArgumentException("해당 유저를 찾을 수 없습니다 !! userId : ${dto.userId}")
                }
                val newResumePhoto = ResumePhoto(
                    user = user,
                    photoUrl = dto.photoUrl,
                    createdAt = LocalDateTime.now()
                )
                val savedResumePhoto = resumePhotoRepsoitory.save(newResumePhoto)
                syncedResumePhotos.add(savedResumePhoto)
                resumePhotoIdsToKeep.add(savedResumePhoto.resumePhotoId)
            } else {
                // 기존 데이터 업데이트
                existingResumePhotos[dto.resumePhotoId]?.let { resumePhoto ->
                    resumePhoto.apply {
                        photoUrl = dto.photoUrl
                    }
                    val updatedResumePhoto = resumePhotoRepsoitory.save(resumePhoto)
                    syncedResumePhotos.add(updatedResumePhoto)
                    resumePhotoIdsToKeep.add(updatedResumePhoto.resumePhotoId)
                }
            }
        }

        // 요청에 포함되지 않은 데이터 삭제
        val resumePhotoIdsToDelete = existingResumePhotos.keys - resumePhotoIdsToKeep
        if(resumePhotoIdsToDelete.isNotEmpty()) {
            resumePhotoRepsoitory.deleteAllById(resumePhotoIdsToDelete)
        }

        return syncedResumePhotos
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