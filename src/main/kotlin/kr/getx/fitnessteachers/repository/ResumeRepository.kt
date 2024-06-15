package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.Resume
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface ResumeRepository : JpaRepository<Resume, Int> {
    fun findAllByUserUserId(userId: Int): List<Resume>

    fun findByResumeId(resumeId: Int): Resume?

    fun findAllByResumeIdIn(resumeIds: List<Int>): List<Resume>

    // SoftDelete
    fun findByResumeIdAndIsDeletedFalse(resumeId: Int): Optional<Resume>

    // hard Delete
    fun findByIsDeletedTrueAndIsDeletedAtBefore(expiredDateTime: LocalDateTime): List<Resume>
}