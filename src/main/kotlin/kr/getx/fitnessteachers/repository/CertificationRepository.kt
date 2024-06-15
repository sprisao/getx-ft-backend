package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.Certification
import kr.getx.fitnessteachers.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Optional

@Repository
interface CertificationRepository : JpaRepository<Certification, Int> {
    fun findByCertificationIdIn(certificationIds: List<Int>): List<Certification>

    fun findByUser(user: User): List<Certification>

    // Soft Delete
    fun findAllByCertificationIdInAndIsDeletedFalse(certificationIds: List<Int>): List<Certification>

    fun findByIsDeletedTrueAndIsDeletedAtBefore(expiredDateTime: LocalDateTime): List<Certification>
}
