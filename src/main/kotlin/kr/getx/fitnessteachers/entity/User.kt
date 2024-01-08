package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import lombok.Getter
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime


@DynamicUpdate
@Entity
@Getter
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    val userId: Int,

    @Column(name = "Username")
    var username: String = "",

    @Column(name = "Email")
    var email: String = "",

    @Column(name = "PhoneNumber")
    var phoneNumber: String = "",

    @Column(name = "UserType")
    val userType: String? = "",

    @Column(name = "ExperienceYears")
    val experienceYears: Int? = null,

    @Column(name = "CreatedAt", insertable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "User_Social_Media_ID")
    val userSocialMediaId: String = ""
)