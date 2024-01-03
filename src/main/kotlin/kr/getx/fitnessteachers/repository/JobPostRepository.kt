package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.JobPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JobPostRepository : JpaRepository<JobPost, Int> {
    // 필요한 경우 사용자 정의 메서드를 추가
}
