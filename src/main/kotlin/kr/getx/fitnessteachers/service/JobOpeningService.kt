package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.JobOpening
import kr.getx.fitnessteachers.repository.JobOpeningRepository
import org.springframework.stereotype.Service

@Service
class JobOpeningService(private val jobOpeningRepository: JobOpeningRepository) {

    fun getAllJobPosts(): List<JobOpening> = jobOpeningRepository.findAll()

    fun addJobPost(jobOpening: JobOpening): JobOpening = jobOpeningRepository.save(jobOpening)

    fun getJobPostById(id: Int): JobOpening? = jobOpeningRepository.findById(id).orElse(null)

    fun updateJobPost(jobOpening: JobOpening): JobOpening = jobOpeningRepository.save(jobOpening)

    fun deleteJobPost(id: Int) = jobOpeningRepository.deleteById(id)
}
