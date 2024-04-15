package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.Education
import kr.getx.fitnessteachers.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EducationRepository : JpaRepository<Education, Int> {
    fun findByEducationIdIn(educationIds: List<Int>): List<Education>

    fun findByUser(user: User): List<Education>
}
