package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "centers")
data class Center(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CenterID")
    val centerId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    val user: User,

    @Column(name = "CenterName")
    val centerName: String? = null,

    @Column(name = "Location")
    val location: String? = null,

    @Column(name = "Description")
    val description: String? = null,

    @Column(name = "CreatedAt")
    val createdAt: LocalDateTime? = LocalDateTime.now()
)