package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "certifications")
data class Certification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CertificationID")
    val certificationId: Int,

    @ManyToOne
    @JoinColumn(name = "ResumeID", referencedColumnName = "ResumeID")
    val resume: Resume,

    @Column(name = "Name")
    val name: String,

    @Column(name = "IssuedBy")
    val issuedBy: String,

    @Column(name = "IssuedDate")
    val issuedDate: LocalDate,

    @Column(name = "CreatedAt")
    val createdAt: LocalDateTime = LocalDateTime.now()
)
