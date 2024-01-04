package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import java.time.LocalDateTime
@Entity
@Table(name = "centerPhotos")
data class CenterPhoto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PhotoID")
    val photoId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "CenterID", referencedColumnName = "CenterID")
    val center: Center? = null,

    @Column(name = "Photo")
    val photo: String? = null,

    @Column(name = "CreatedAt")
    val createdAt: LocalDateTime? = LocalDateTime.now()
)