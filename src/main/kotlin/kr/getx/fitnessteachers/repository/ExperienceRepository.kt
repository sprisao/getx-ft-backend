package kr.getx.fitnessteachers.repository

import java.util.*
import kr.getx.fitnessteachers.entity.Experience
import kr.getx.fitnessteachers.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ExperienceRepository : JpaRepository<Experience, Int> {
    fun findByExperienceIdIn(experienceIds: List<Int>): List<Experience>

    fun findByUser(user: User): List<Experience>

    // Soft Delete
    fun findAllByExperienceIdInAndIsDeletedFalse(experienceIds: List<Int>): List<Experience>

    fun findByIsDeletedTrueAndDeletedAtBefore(expiredDateTime: LocalDateTime): List<Experience>
}
