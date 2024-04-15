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

    @Query("SELECT j FROM JobPost j WHERE " +
            "(:isRecruitmentOpen IS NULL OR j.isRecruitmentOpen = :isRecruitmentOpen) AND " +
            "(:jobCategoriesList IS NULL OR j.jobCategories IN :jobCategoriesList) AND " +
            "(:employmentType IS NULL OR j.employmentType = :employmentType) AND " +
            "(:salaryType IS NULL OR j.salaryType = :salaryType) AND " +
            "(:experienceLevel IS NULL OR j.experienceLevel >= :experienceLevel) AND " +
            "(:sidoEnglish IS NULL OR j.center.sidoEnglish = :sidoEnglish) AND " +
            "(:sigunguEnglish IS NULL OR j.center.sigunguEnglish = :sigunguEnglish) AND " +
            "(:day IS NULL OR EXISTS (SELECT 1 FROM j.workDays wd WHERE wd.day = :day)) AND " +
            "(:startTime IS NULL OR EXISTS (SELECT 1 FROM j.workDays wd WHERE wd.startTime >= :startTime)) AND " +
            "(:endTime IS NULL OR EXISTS (SELECT 1 FROM j.workDays wd WHERE wd.endTime <= :endTime))")
    fun search(
        @Param("isRecruitmentOpen") isRecruitmentOpen: Boolean?,
        @Param("jobCategoriesList") jobCategoriesList: List<String>?,
        @Param("employmentType") employmentType: String?,
        @Param("salaryType") salaryType: String?,
        @Param("experienceLevel") experienceLevel: Int?,
        @Param("sidoEnglish") sidoEnglish: String?,
        @Param("sigunguEnglish") sigunguEnglish: String?,
        @Param("day") day: String?,
        @Param("startTime") startTime: String?,
        @Param("endTime") endTime: String?,
        pageable: Pageable
    ): Page<JobPost>
}
