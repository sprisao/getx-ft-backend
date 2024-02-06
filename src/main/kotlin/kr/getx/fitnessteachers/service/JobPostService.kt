package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.repository.JobPostRepository
import org.springframework.stereotype.Service

@Service
class JobPostService(private val jobPostRepository: JobPostRepository) {

    fun findAll(): List<JobPost> = jobPostRepository.findAll()

    fun findById(id: Int): JobPost? = jobPostRepository.findById(id).orElse(null)

    fun save(jobPost: JobPost): JobPost = jobPostRepository.save(jobPost)

    fun deleteById(id: Int) = jobPostRepository.deleteById(id)
}
