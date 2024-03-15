package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.JobPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Repository
interface JobPostRepository : JpaRepository<JobPost, Int> {

    fun findByCenter_CenterId(centerId: Int): List<JobPost>

    // 검색 기능 추가
    fun findByRecruitmentStatusAndJobCategoryAndCenterLocationProvinceAndCenterLocationCity(
        recruitmentStatus: String?,
        jobCategory: String?,
        locationProvince: String?,
        locationCity: String?,
        pageable: Pageable
    ) : Page<JobPost>
}
