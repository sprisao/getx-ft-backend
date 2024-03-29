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

    @Lob
    var photos: String? = null,

    @ElementCollection
    var appliedJobPostIds : MutableList<Int> = mutableListOf(),

    @CreationTimestamp
    val createdAt: LocalDateTime
)
