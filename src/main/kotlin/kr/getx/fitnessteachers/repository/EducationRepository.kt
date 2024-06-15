package kr.getx.fitnessteachers.repository

import java.util.*
import kr.getx.fitnessteachers.entity.Education
import kr.getx.fitnessteachers.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface EducationRepository : JpaRepository<Education, Int> {
    fun findByEducationIdIn(educationIds: List<Int>): List<Education>

    fun findByUser(user: User): List<Education>

    // Soft Delete
    fun findAllByEducationIdInAndIsDeletedFalse(educationIds: List<Int>): List<Education>

    fun findByIsDeletedTrueAndIsDeletedAtBefore(expiredDateTime: LocalDateTime): List<Education>
}
