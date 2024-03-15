package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.Center
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
@Repository
interface CenterRepository : JpaRepository<Center, Int> {
    fun findByUser_UserId(userId: Int): List<Center>

    // 센터 검색 기능

    fun findByCenterNameAndLocationProvinceAndLocationCity(centerName: String?, locationProvince: String?, locationCity: String?, pageable: Pageable): Page<Center>
}
