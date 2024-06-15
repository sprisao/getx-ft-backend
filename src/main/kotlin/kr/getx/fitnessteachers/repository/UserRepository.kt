package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.*

interface UserRepository : JpaRepository<User, Int>{
    // 중복 이메일 검사 메소드
    fun findByEmail(email: String?): User?

    // Soft Delete 처리
    fun findByEmailAndIsDeletedFalse(email: String): Optional<User>

    // hard delete
    fun findByIsDeletedTrueAndIsDeletedAtBefore(expiredDateTime: LocalDateTime): List<User>
}