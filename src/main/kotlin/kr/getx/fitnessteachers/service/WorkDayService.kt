package kr.getx.fitnessteachers.service

import jakarta.transaction.Transactional
import kr.getx.fitnessteachers.repository.WorkDayRepository
import org.springframework.stereotype.Service

@Service
class WorkDayService (
    private val workDayRepository: WorkDayRepository
) {
    @Transactional
    fun deleteByJobPostJobPostId(jobPostId: Int) = workDayRepository.deleteByJobPostJobPostId(jobPostId)
}