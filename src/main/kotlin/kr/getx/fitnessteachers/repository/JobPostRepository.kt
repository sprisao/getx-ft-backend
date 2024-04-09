package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.JobPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query

@Repository
interface JobPostRepository : JpaRepository<JobPost, Int> {
    fun findByCenterCenterId(centerId: Int): List<JobPost>

//    @Query("SELECT j FROM JobPost j WHERE " +
//            "(:recruitmentStatus IS NULL OR j.recruitmentStatus = :recruitmentStatus) AND " +
//            "(:jobCategory IS NULL OR j.jobCategory = :jobCategory) AND " +
//            "(:sidoEnglish IS NULL OR j.center.sidoEnglish = :sidoEnglish) AND " +
//            "(:sigunguEnglish IS NULL OR j.center.sigunguEnglish = :sigunguEnglish)")
//
//    fun search(
//        recruitmentStatus: Boolean?,
//        jobCategory: String?,
//        sidoEnglish: String?,
//        sigunguEnglish: String?,
//        pageable: Pageable
//    ) : Page<JobPost>
}
