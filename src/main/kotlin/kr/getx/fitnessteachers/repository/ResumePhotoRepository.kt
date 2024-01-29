package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.ResumePhoto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ResumePhotoRepository : JpaRepository<ResumePhoto, Int> {
    fun findByResumePhotoResumeId(resumeId: Int): List<ResumePhoto>

    fun deleteByResumePhotoResumeId(resumeId: Int)
}