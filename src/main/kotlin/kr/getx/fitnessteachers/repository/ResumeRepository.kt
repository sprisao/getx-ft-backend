package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.Resume
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ResumeRepository : JpaRepository<Resume, Int> {
    fun findAllByUserUserId(userId: Int): List<Resume>

    fun findByResumeId(resumeId: Int): Resume?

    fun findAllByResumeIdIn(resumeIds: List<Int>): List<Resume>

    // SoftDelete
    fun findByResumeIdAndDeletedFalse(resumeId: Int): Optional<Resume>
}