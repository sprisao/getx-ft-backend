package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.Resume
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Repository
interface ResumeRepository : JpaRepository<Resume, Int> {
    fun findByUserUserId(userId: Int): Resume?

    // 검색 기능
    fun findByCriteria(keyword: String?, experienceYears: Int?, educationLevel: String?, pageable: Pageable): Page<Resume>
}

