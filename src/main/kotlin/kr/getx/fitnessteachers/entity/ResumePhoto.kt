package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "resumePhotos")
data class ResumePhoto (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val resumePhotoId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    val user: User,

    var photoUrl: String,

    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
)