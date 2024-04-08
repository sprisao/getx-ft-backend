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

    val description: String?,

    @Lob
    val photos: String?,

    val mainPhoto: String?,

    // 이력서 공개 여부
    val isDisplay: Boolean?,

    // 이력서 수정 상태
    val isEditing: Boolean?,


    @CreationTimestamp
    val createdAt: LocalDateTime
)
