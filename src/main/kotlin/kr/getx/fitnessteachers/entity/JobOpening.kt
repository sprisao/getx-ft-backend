package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "job_openings")
data class JobOpening(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val jobOpeningId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "centerId", referencedColumnName = "centerId")
    val center: Center? = null,

    val recruitmentStatus: String? = null,

    val responsibilities: String? = null,

    val workLocation: String? = null,

    val workHours: String? = null,

    val workDays: String? = null,

    val employmentType: String? = null,

    val numberOfPositions: Int? = null,

    val salary: String? = null,

    val baseSalary: String? = null,

    val qualifications: String? = null,

    val applicationPeriodStart: LocalDateTime? = null,

    val applicationPeriodEnd: LocalDateTime? = null,

    val contactEmail: String? = null,

    val contactPhone: String? = null,

    val contactPerson: String? = null,

    val details: String? = null,

    @CreationTimestamp
    val postedDate: LocalDateTime? = null
)