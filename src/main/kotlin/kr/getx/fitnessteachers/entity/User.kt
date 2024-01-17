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
    val userId: Int = 0,

    val name: String = "",

    val email: String? = "",

    var profileUrl: String? = "",

    var socialType: String = "",

    var userType: String? = "",

    var profileStatus: Boolean? = false,

    var resumeStatus: Boolean? = false,

    var centerStatus: Boolean? = false,

    @Column(name = "createdAt", insertable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now(),
)