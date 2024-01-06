package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "resumes")
data class Resume(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ResumeID")
    val resumeId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "UserID")
    val user: User,

    @Column(name = "Photo")
    val photo: String? = null,

    @Column(name = "CreatedAt")
    val createdAt: LocalDateTime? = LocalDateTime.now()
)
