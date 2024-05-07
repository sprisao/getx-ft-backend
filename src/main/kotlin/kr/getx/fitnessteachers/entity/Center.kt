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

    var roadAddress: String? = null,

    var sido: String? = null,

    var sidoEnglish: String? = null,

    var sigungu: String? = null,

    var sigunguEnglish: String? = null,

    var description: String? = null,

    var isDeleted: Boolean = false,
    
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
)