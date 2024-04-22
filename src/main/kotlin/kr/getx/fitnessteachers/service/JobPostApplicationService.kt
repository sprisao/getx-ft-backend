package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.*
import kr.getx.fitnessteachers.entity.JobPostApplication
import kr.getx.fitnessteachers.repository.JobPostApplicationRepository
import kr.getx.fitnessteachers.repository.ResumeRepository
import org.springframework.stereotype.Service
@Service
class JobPostApplicationService(
    private val jobPostApplicationRepository: JobPostApplicationRepository,
    private val jobPostService: JobPostService,
    private val resumeService: ResumeService,
    private val resumeRepository: ResumeRepository,
    private val userService: UserService,
    ){
    fun applyToJobPost(resumeId: Int, jobPostId: Int): JobPostApplication {
        val jobPost = jobPostService.findById(jobPostId)
            ?: throw IllegalArgumentException("해당 구직 공고가 존재하지 않습니다.")
        val resume = resumeRepository.findByResumeId(resumeId)
            ?: throw IllegalArgumentException("해당 이력서가 존재하지 않습니다.")
        val user = userService.findUserById(resume.user.userId)

        return jobPostApplicationRepository.save(JobPostApplication(jobPost = jobPost, resume = resume, user = user))
    }

    fun cancelApplication(resumeId: Int, jobPostId: Int) {
        val application = jobPostApplicationRepository.findByResumeResumeIdAndJobPostJobPostId(resumeId, jobPostId)
            ?: throw IllegalArgumentException("해당 사용자가 해당 구직 공고에 지원한 내역이 존재하지 않습니다.")
        jobPostApplicationRepository.delete(application)
    }

    fun getApplicantCount(jobPostId: Int): Int =
        jobPostApplicationRepository.countByJobPostJobPostId(jobPostId)

    fun getApplicantResumes(jobPostId: Int): List<ResumeDto> {
        val jobPostApplications = jobPostApplicationRepository.findByJobPostJobPostId(jobPostId)
        val resumeIds = jobPostApplications.map { it.resume.resumeId }
        val resumes = resumeService.getResumesByResumeIds(resumeIds)
        return resumes.map { resume ->
            val createAt = jobPostApplications.find { it.user.userId == resume.user.userId }!!.createdAt
            resumeService.toDto(resume).copy(createdAt = createAt)
        }
    }

    fun getAppliedJobPosts(userId: Int): List<JobPostDto> =
        jobPostApplicationRepository.findByUserUserId(userId).map {
            JobPostDto.fromEntity(it.jobPost)
        }
}