package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "workDays")
data class WorkDay (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val workDayId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "jobPostId", referencedColumnName = "jobPostId")
    val jobPost: JobPost,

    var day: String,

    var startTime: String,

    var endTime: String,

    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
)