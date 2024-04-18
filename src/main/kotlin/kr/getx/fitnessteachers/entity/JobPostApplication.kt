package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "jobPostApplications")
data class JobPostApplication(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val jobPostApplicationId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "jobPostId", referencedColumnName = "jobPostId")
    val jobPost: JobPost,

    @ManyToOne
    @JoinColumn(name = "resumeId", referencedColumnName = "resumeId")
    val resume: Resume,

    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
)
