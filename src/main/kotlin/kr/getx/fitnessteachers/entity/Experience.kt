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
    @JoinColumn(name = "resumeId", referencedColumnName = "resumeId")
    val resume: Resume,

    @Column(name = "description")
    val description: String,

    @Column(name = "startDate")
    val startDate: LocalDate,

    @Column(name = "endDate")
    val endDate: LocalDate,

    @CreationTimestamp
    val createdAt: LocalDateTime? = null
)
