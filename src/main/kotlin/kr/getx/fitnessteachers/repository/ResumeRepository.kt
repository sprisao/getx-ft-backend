package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.Resume
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ResumeRepository : JpaRepository<Resume, Int> {
    fun findByUserUserId(userId: Int): Resume?

    fun findAllByUserUserId(userId: Int): List<Resume>

    fun findByResumeId(resumeId: Int): Resume?
}