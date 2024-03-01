package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.Education
import kr.getx.fitnessteachers.entity.Resume
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EducationRepository : JpaRepository<Education, Int> {
    // 필요한 경우 사용자 정의 메서드를 추가
    fun findByResumeResumeId(resumeId: Int): List<Education>

    fun deleteAllByResume(resume: Resume)
}
