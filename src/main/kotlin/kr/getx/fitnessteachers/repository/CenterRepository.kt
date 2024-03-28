package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.Center
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
@Repository
interface CenterRepository : JpaRepository<Center, Int> {
    fun findByUser_UserId(userId: Int): List<Center>
}
