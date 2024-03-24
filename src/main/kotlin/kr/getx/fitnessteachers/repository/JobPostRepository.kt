package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.JobPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@Repository
interface JobPostRepository : JpaRepository<JobPost, Int> {
    fun findByCenterCenterId(centerId: Int): List<JobPost>
    fun search(
        recruitmentStatus: String?,
        jobCategory: String?,
        locationProvince: String?,
        locationCity: String?,
        pageable: Pageable
    ) : Page<JobPost>
}
