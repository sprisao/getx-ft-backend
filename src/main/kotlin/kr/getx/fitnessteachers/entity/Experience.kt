package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "experiences")
data class Experience(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val experienceId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    val user: User,

    var description: String,

    var startDate: LocalDate,

    var endDate: LocalDate,

    var isDeleted: Boolean = false,

    var isDeletedAt: LocalDateTime? = null,

    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
)
