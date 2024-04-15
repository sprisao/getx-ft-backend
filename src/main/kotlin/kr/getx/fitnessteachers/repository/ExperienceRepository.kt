package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.Experience
import kr.getx.fitnessteachers.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExperienceRepository : JpaRepository<Experience, Int> {
    fun findByExperienceIdIn(experienceIds: List<Int>): List<Experience>

    fun findByUser(user: User): List<Experience>
}
