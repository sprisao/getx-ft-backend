package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.Center
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Optional

@Repository
interface CenterRepository : JpaRepository<Center, Int> {
    fun findByUser_UserId(userId: Int): List<Center>

    // SoftDelete
    fun findByCenterIdAndIsDeletedFalse(centerId: Int): Optional<Center>

    fun findByIsDeletedTrueAndDeletedAtBefore(expiredDateTime: LocalDateTime): List<Center>
}
