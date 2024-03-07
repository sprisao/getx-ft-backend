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
    @JoinColumn(name = "resumeId", referencedColumnName = "resumeId")
    val resume: Resume,

    var name: String,

    var issuedBy: LocalDate,

    var issuedDate: LocalDate,

    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
)
