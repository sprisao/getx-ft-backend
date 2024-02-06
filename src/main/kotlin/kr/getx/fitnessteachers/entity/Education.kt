package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "educations")
data class Education(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val educationId: Int,

    @ManyToOne
    @JoinColumn(name = "resumeId", referencedColumnName = "resumeId")
    val resume: Resume,

    val courseName: String,

    val institution: String,

    val completionDate: LocalDate,

    @CreationTimestamp
    val createdAt: LocalDateTime? = null
)
