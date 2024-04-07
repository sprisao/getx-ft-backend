package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "resumes")
data class Resume(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val resumeId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    val user: User,

    var description: String? = null,

    @Lob
    var photos: String? = null,

    val mainPhoto: String? = null,

    val isDisplay: Boolean = true,

    val isEditing: Boolean = false,

    @CreationTimestamp
    val createdAt: LocalDateTime
)
