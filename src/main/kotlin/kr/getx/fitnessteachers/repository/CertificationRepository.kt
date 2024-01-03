package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.Certification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CertificationRepository : JpaRepository<Certification, Int> {
    // 필요한 경우 사용자 정의 메서드를 추가
}
