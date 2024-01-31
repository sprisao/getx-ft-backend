package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.JobSeeker
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JobSeekerRepository : JpaRepository<JobSeeker, Int>
