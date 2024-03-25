package kr.getx.fitnessteachers.entity

import jakarta.persistence.*

@Entity
@Table(name = "locations")
class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val locationId: Int = 0

    val name: String = ""

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: Location? = null

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val children: List<Location> = mutableListOf()
}