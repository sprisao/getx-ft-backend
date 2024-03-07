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
    val educationId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "resumeId", referencedColumnName = "resumeId")
    var resume: Resume,

    var courseName: String,

    var institution: String,

    var completionDate: LocalDate,

    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
)
