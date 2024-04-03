package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "jobPostFavorites")
data class JobPostFavorite(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val jobPostFavoriteId: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobPostId", referencedColumnName = "jobPostId")
    val jobPost: JobPost,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    val user: User,

    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
)