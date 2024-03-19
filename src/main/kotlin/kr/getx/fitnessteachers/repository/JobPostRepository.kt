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

    fun findByCenter_CenterId(centerId: Int): List<JobPost>

    // 검색 기능 추가
    @Query("SELECT jp FROM JobPost jp WHERE (:recruitmentStatus IS NULL OR jp.recruitmentStatus = :recruitmentStatus) " +
            "AND (:jobCategory IS NULL OR jp.jobCategory = :jobCategory) " +
            "AND (:locationProvince IS NULL OR jp.center.locationProvince = :locationProvince) " +
            "AND (:locationCity IS NULL OR jp.center.locationCity = :locationCity)")
    fun findByRecruitmentStatusAndJobCategoryAndCenterLocationProvinceAndCenterLocationCity(
        @Param("recruitmentStatus") recruitmentStatus: String?,
        @Param("jobCategory") jobCategory: String?,
        @Param("locationProvince") locationProvince: String?,
        @Param("locationCity") locationCity: String?,
        pageable: Pageable
    ) : Page<JobPost>
}
