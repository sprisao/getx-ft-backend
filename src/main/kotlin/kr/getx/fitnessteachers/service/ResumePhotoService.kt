package kr.getx.fitnessteachers.service

import java.time.LocalDateTime
import kr.getx.fitnessteachers.dto.ResumePhotoDto
import kr.getx.fitnessteachers.entity.ResumePhoto
import kr.getx.fitnessteachers.repository.ResumePhotoRepository
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ResumePhotoService(
        private val resumePhotoRepsoitory: ResumePhotoRepository,
        private val userRepository: UserRepository
) {
    fun getAllResumePhotos(): List<ResumePhoto> = resumePhotoRepsoitory.findAll()

    fun findResumePhotosByUserIds(userId: Int): List<ResumePhoto> {
        val user =
                userRepository.findById(userId).orElseThrow {
                    IllegalArgumentException("해당 유저를 찾을수 없습니다 !! userId : $userId")
                }
        return resumePhotoRepsoitory.findByUser(user)
    }

    fun syncResumePhotos(resumePhotoDtos: List<ResumePhotoDto>): List<ResumePhoto> {
        val existingResumePhotos = resumePhotoRepsoitory.findAll().associateBy { it.resumePhotoId }
        val resumePhotoIdsToKeep = mutableSetOf<Int>()
        val syncedResumePhotos = mutableListOf<ResumePhoto>()

        resumePhotoDtos.forEach { dto ->
            if (dto.resumePhotoId == 0) { // id 값이 0 일때 추가
                // 새로운 데이터 추가
                val user =
                        userRepository.findById(dto.userId).orElseThrow {
                            IllegalArgumentException("해당 유저를 찾을 수 없습니다 !! userId : ${dto.userId}")
                        }
                val newResumePhoto =
                        ResumePhoto(
                                user = user,
                                photoUrl = dto.photoUrl,
                                isDeleted = false,
                                createdAt = LocalDateTime.now()
                        )
                val savedResumePhoto = resumePhotoRepsoitory.save(newResumePhoto)
                syncedResumePhotos.add(savedResumePhoto)
                resumePhotoIdsToKeep.add(savedResumePhoto.resumePhotoId)
            } else {
                // 기존 데이터 업데이트
                val resumePhoto =
                        existingResumePhotos[dto.resumePhotoId]?.apply { photoUrl = dto.photoUrl }
                if (resumePhoto != null) {
                    syncedResumePhotos.add(resumePhotoRepsoitory.save(resumePhoto))
                    resumePhotoIdsToKeep.add(resumePhoto.resumePhotoId)
                }
            }
        }

        // 요청에 포함되지 않은 데이터 삭제
        val resumePhotoIdsToDelete = existingResumePhotos.keys - resumePhotoIdsToKeep
        if (resumePhotoIdsToDelete.isNotEmpty()) {
            resumePhotoRepsoitory.deleteAllById(resumePhotoIdsToDelete)
        }

        return syncedResumePhotos
    }

    fun updateResumePhotos(resumePhotoDtos: List<ResumePhotoDto>): List<ResumePhoto> {
        val resumePhotoIds = resumePhotoDtos.mapNotNull { it.resumePhotoId }
        val existingResumePhotos =
                resumePhotoRepsoitory.findByResumePhotoIdIn(resumePhotoIds).associateBy {
                    it.resumePhotoId
                }

        return resumePhotoDtos.mapNotNull { dto ->
            val resumePhoto =
                    existingResumePhotos[dto.resumePhotoId]?.apply { photoUrl = dto.photoUrl }
                            ?: return@mapNotNull null
            resumePhotoRepsoitory.save(resumePhoto)
        }
    }

    fun deleteResumePhotos(resumePhotoIds: List<Int>) {
        val resumePhotos =
                resumePhotoRepsoitory.findAllByResumePhotoIdInAndIsDeletedFalse(resumePhotoIds)
        if (resumePhotos.isEmpty()) {
            throw IllegalArgumentException("해당 이력사진이 존재하지 않습니다.")
        }
        resumePhotos.forEach { resumePhotos ->
            resumePhotos.isDeleted = true
            resumePhotos.isDeletedAt = LocalDateTime.now()
        }
        resumePhotoRepsoitory.saveAll(resumePhotos)
    }

    fun findResumePhotosByIds(resumePhotoIds: List<Int>): List<ResumePhoto> =
            resumePhotoRepsoitory.findByResumePhotoIdIn(resumePhotoIds)
}
