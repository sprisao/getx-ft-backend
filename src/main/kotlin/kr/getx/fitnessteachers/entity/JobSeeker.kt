package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "job_seekers")
data class JobSeeker(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val jobSeekerId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "resumeId", referencedColumnName = "resumeId")
    val resume: Resume,

    @Enumerated(EnumType.STRING)
    val jobInterest: String,

    @Enumerated(EnumType.STRING)
    val availabilityStatus: String,

    val desiredWorkLocation: String? = null,

    val expectedSalary: String? = null,

    val workExperienceYears: Int? = null,

    val specialQualifications: String? = null,

    val introduction: String? = null,

    val availabilityStart: LocalDateTime? = null,

    val availabilityEnd: LocalDateTime? = null,

    val contactEmail: String? = null,

    val contactPhone: String? = null,

    @CreationTimestamp
    val postedDate: LocalDateTime = LocalDateTime.now()
)