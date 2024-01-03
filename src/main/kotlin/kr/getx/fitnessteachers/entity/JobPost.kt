package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "jobPosts")
data class JobPost(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JobPostID")
    val jobPostId: Int,

    @ManyToOne
    @JoinColumn(name = "CenterID", referencedColumnName = "CenterID")
    val center: Center,

    @Column(name = "RecruitmentStatus")
    val recruitmentStatus: String,

    @Column(name = "JobType")
    val jobType: String,

    @Column(name = "Responsibilities")
    val responsibilities: String,

    @Column(name = "WorkLocation")
    val workLocation: String,

    @Column(name = "WorkDays")
    val workDays: String,

    @Column(name = "EmploymentType")
    val employmentType: String,

    @Column(name = "NumberOfPositions")
    val numberOfPositions: Int,

    @Column(name = "Salary")
    val salary: String,

    @Column(name = "Qualifications")
    val qualifications: String,

    @Column(name = "ApplicationPeriodStart")
    val applicationPeriodStart: LocalDateTime,

    @Column(name = "ApplicationPeriodEnd")
    val applicationPeriodEnd: LocalDateTime,

    @Column(name = "ContactEmail")
    val contactEmail: String,

    @Column(name = "ContactPhone")
    val contactPhone: String,

    @Column(name = "ContactPerson")
    val contactPerson: String,

    @Column(name = "Details")
    val details: String,

    @Column(name = "PostedDate")
    val postedDate: LocalDateTime = LocalDateTime.now()
)
