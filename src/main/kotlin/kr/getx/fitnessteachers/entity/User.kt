package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import lombok.Getter
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime


@DynamicUpdate
@Entity
@Getter
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Int = 0,

    val name: String,

    var nickname: String,

    @Column(name = "email", unique = true)
    val email: String,

    var photo: String?,

    var socialType: String,

    var userType: Boolean?,

    @Enumerated(EnumType.STRING)
    var teacherType: TeacherType,

    var photoIsDisplay: Boolean,

    var resumeExists: Boolean,

    var centerExists: Boolean,

    var userTypeStatus: Boolean,

    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
)

enum class TeacherType {
    YOGA,
    PILATES,
    FITNESS
}