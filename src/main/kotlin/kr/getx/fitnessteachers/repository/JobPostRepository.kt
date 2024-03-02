package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.JobPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JobPostRepository : JpaRepository<JobPost, Int> {

    fun findByCenter_CenterId(centerId: Int): List<JobPost>
}
