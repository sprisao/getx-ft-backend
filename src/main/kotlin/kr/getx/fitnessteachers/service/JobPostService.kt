package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.repository.JobPostRepository
import org.springframework.stereotype.Service

@Service
class JobPostService(private val jobPostRepository: JobPostRepository) {

    fun getAllJobPosts(): List<JobPost> = jobPostRepository.findAll()

    fun addJobPost(jobPost: JobPost): JobPost = jobPostRepository.save(jobPost)

    fun getJobPostById(id: Int): JobPost? = jobPostRepository.findById(id).orElse(null)

    fun updateJobPost(jobPost: JobPost): JobPost = jobPostRepository.save(jobPost)

    fun deleteJobPost(id: Int) = jobPostRepository.deleteById(id)

    // 추가적인 필요한 메소드들
}
