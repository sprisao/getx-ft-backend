package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
@Entity
@Table(name = "centersPhotos")
data class CenterPhoto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val photoId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "centerId", referencedColumnName = "centerId")
    val center: Center,

    val photoUrl: String? = null,

    @CreationTimestamp
    val createdAt: LocalDateTime? = null
)