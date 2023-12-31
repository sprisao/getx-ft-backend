package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val username: String = "",
    val email: String = "",
    val phoneNumber: String? = null,
    val userType: String = "",
    val experienceYears: Int? = null,
    @Column(name = "CreatedAt", insertable = false)
    val createdAt: LocalDateTime? = null
)