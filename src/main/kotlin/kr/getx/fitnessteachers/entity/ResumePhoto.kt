package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import org.hibernate.annotations.CreationTimestamp

@Entity
@Table(name = "resumesPhotos")
data class ResumePhoto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val photoId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "resumeId", referencedColumnName = "resumeId")
    val resume: Resume,

    val photoUrl: String? = null,

    @CreationTimestamp
    val createdAt: LocalDateTime? = null
)
