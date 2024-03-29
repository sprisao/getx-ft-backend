package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "centers")
data class Center(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val centerId: Int = 0,

        @ManyToOne
        @JoinColumn(name = "userId", referencedColumnName = "userId")
        val user: User,

        var centerName: String? = null,

        @Lob
        var photos: String? = null,

        var locationProvince: String? = null,

        var locationCity: String? = null,

        var description: String? = null,

        @CreationTimestamp
        val createdAt: LocalDateTime = LocalDateTime.now()
)