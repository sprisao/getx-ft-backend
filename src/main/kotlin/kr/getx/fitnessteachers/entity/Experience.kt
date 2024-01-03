package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "experiences")
data class Experience(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExperienceID")
    val experienceId: Int,

    @ManyToOne
    @JoinColumn(name = "ResumeID", referencedColumnName = "ResumeID")
    val resume: Resume,

    @Column(name = "Description")
    val description: String,

    @Column(name = "StartDate")
    val startDate: LocalDate,

    @Column(name = "EndDate")
    val endDate: LocalDate,

    @Column(name = "CreatedAt")
    val createdAt: LocalDateTime = LocalDateTime.now()
)
