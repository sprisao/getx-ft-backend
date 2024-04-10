package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.WorkDay
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository

@Repository
interface WorkDayRepository : JpaRepository<WorkDay, Int>{
}