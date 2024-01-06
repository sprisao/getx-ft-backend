package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    val userId: Int = 0,
    @Column(name = "User_Social_Media_ID")
    val userSocialMediaId: String = "",
    @Column(name = "Username")
    var username: String = "",
    @Column(name = "Email")
    var email: String = "",
    @Column(name = "PhoneNumber")
    var phoneNumber: String? = null,
    @Column(name = "UserType")
    val userType: String = "",
    @Column(name = "ExperienceYears")
    val experienceYears: Int? = null,
    @Column(name = "CreatedAt", insertable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now()
)