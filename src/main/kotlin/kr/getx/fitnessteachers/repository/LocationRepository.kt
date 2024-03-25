package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.Location
import org.springframework.data.jpa.repository.JpaRepository

interface LocationRepository : JpaRepository<Location, Int>{
    fun findCitiesByParent(parent: Location): List<Location>
}