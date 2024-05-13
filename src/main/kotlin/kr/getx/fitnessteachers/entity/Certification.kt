package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "certifications")
data class Certification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val certificationId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    val user: User,

    var name: String,

    var issuedBy: String,

    var issuedDate: LocalDate,

    var isDeleted: Boolean = false,

    var isDeletedAt: LocalDateTime? = null,

    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
)
