package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "educations")
data class Education(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EducationID")
    val educationId: Int,

    @ManyToOne
    @JoinColumn(name = "ResumeID", referencedColumnName = "ResumeID")
    val resume: Resume,

    @Column(name = "CourseName")
    val courseName: String,

    @Column(name = "Institution")
    val institution: String,

    @Column(name = "CompletionDate")
    val completionDate: LocalDate,

    @Column(name = "CreatedAt")
    val createdAt: LocalDateTime = LocalDateTime.now()
)
