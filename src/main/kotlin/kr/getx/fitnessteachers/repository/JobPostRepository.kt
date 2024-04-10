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

    @Query("SELECT j FROM JobPost j JOIN j.workDays wd WHERE " +
            "(:isRecruitmentOpen IS NULL OR j.isRecruitmentOpen = :isRecruitmentOpen) AND " +
            "(:jobCategories IS NULL OR EXISTS (SELECT 1 FROM j.jobCategories jc WHERE jc IN :jobCategories)) AND " +
            "(:employmentType IS NULL OR j.employmentType = :employmentType) AND " +
            "(:hasBaseSalary IS NULL OR j.hasBaseSalary = :hasBaseSalary) AND " +
            "(:experienceLevel IS NULL OR j.experienceLevel >= :experienceLevel) AND " +
            "(:sidoEnglish IS NULL OR j.center.sidoEnglish = :sidoEnglish) AND " +
            "(:sigunguEnglish IS NULL OR j.center.sigunguEnglish = :sigunguEnglish) AND " +
            "(:day IS NULL OR wd.day = :day) AND " +
            "(:startTime IS NULL OR wd.startTime >= :startTime) AND " +
            "(:endTime IS NULL OR wd.endTime <= :endTime)")
    fun search(
        @Param("isRecruitmentOpen") isRecruitmentOpen: Boolean?,
        @Param("jobCategories") jobCategories: List<String>?,
        @Param("employmentType") employmentType: String?,
        @Param("hasBaseSalary") hasBaseSalary: Boolean?,
        @Param("experienceLevel") experienceLevel: Int?,
        @Param("sidoEnglish") sidoEnglish: String?,
        @Param("sigunguEnglish") sigunguEnglish: String?,
        @Param("day") day: String?,
        @Param("startTime") startTime: String?,
        @Param("endTime") endTime: String?,
        pageable: Pageable
    ): Page<JobPost>
}
