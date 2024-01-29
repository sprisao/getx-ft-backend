package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.ResumePhoto
import kr.getx.fitnessteachers.repository.ResumePhotoRepository
import org.springframework.stereotype.Service

@Service
class ResumePhotoService(private val resumePhotoRepository: ResumePhotoRepository) {

    fun getAllResumePhotos() = resumePhotoRepository.findAll()

    fun getResumePhotoById(id: Int) = resumePhotoRepository.findById(id).orElse(null)

    fun getResumePhotoByResumeId(resumeId: Int) = resumePhotoRepository.findByResumePhotoResumeId(resumeId)

    fun addResumePhoto(resumePhoto: ResumePhoto) = resumePhotoRepository.save(resumePhoto)

    fun updateResumePhoto(resumePhoto: ResumePhoto) = resumePhotoRepository.save(resumePhoto)

    fun deleteResumePhoto(id: Int) = resumePhotoRepository.deleteById(id)

    fun deleteResumePhotoByResumeId(resumeId: Int) = resumePhotoRepository.deleteByResumePhotoResumeId(resumeId)
}