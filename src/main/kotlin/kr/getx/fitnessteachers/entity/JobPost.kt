package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "jobPosts")
data class JobPost(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val jobPostId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "centerId", referencedColumnName = "centerId")
    val center: Center,

    // 작성 전, 작성 완료
    var isDisplayReady: Boolean? = false,

    // 모집중, 모집마감
    var recruitmentStatus: Boolean? = false,

    @Column(length = 10000)
    var responsibilities: String? = null,

    var workLocation: String? = null,

    var workHours: String? = null,

    var workDays: String? = null,

    var employmentType: String? = null,

    var numberOfPositions: Int? = null,

    var salary: String? = null,

    @Column(length = 10000)
    var qualifications: String? = null,

    var applicationPeriodStart: LocalDate? = null,

    var applicationPeriodEnd: LocalDate? = null,

    var contactEmail: String? = null,

    var contactPhone: String? = null,

    var contactPerson: String? = null,

    var title: String? = null,

    @Column(length = 10000)
    var details: String? = null,

    var jobCategory: String? = null,

    @CreationTimestamp
    val postedDate: LocalDateTime = LocalDateTime.now()
)
