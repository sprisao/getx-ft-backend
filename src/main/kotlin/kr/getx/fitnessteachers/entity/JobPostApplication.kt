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

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    val user: User,

    var isDeleted: Boolean = false,

    var isDeletedAt: LocalDateTime? = null,

    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
)
