package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.CertificationDto
import kr.getx.fitnessteachers.dto.EducationDto
import kr.getx.fitnessteachers.dto.ExperienceDto
import kr.getx.fitnessteachers.dto.ResumeDto
import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.entity.JobPostApplication
import kr.getx.fitnessteachers.repository.JobPostApplicationRepository
import kr.getx.fitnessteachers.repository.JobPostRepository
import kr.getx.fitnessteachers.utils.StringConversionUtils
import org.springframework.stereotype.Service
@Service
class JobPostApplicationService (
    private val jobPostApplicationRepository: JobPostApplicationRepository,
    private val jobPostService: JobPostService,
    private val userService: UserService,
    private val jobPostRepository: JobPostRepository,
    private val resumeService: ResumeService,
    private val experienceService: ExperienceService,
    private val educationService: EducationService,
    private val certificationService: CertificationService,
    ){
    fun applyToJobPost(userId: Int, jobPostId: Int): JobPostApplication {
        val jobPost = jobPostService.findById(jobPostId)
            ?: throw IllegalArgumentException("해당 구직 공고가 존재하지 않습니다.")
        val user = userService.findUserById(userId)
            ?: throw IllegalArgumentException("해당 사용자가 존재하지 않습니다.")

        val application = JobPostApplication(
            jobPost = jobPost,
            user = user,
        )

        return jobPostApplicationRepository.save(application)
    }

    fun cancelApplication(userId: Int, jobPostId: Int) {
        val jobPost = jobPostService.findById(jobPostId)
            ?: throw IllegalArgumentException("해당 구직 공고가 존재하지 않습니다.")
        val user = userService.findUserById(userId)
            ?: throw IllegalArgumentException("해당 사용자가 존재하지 않습니다.")

        val application = jobPostApplicationRepository.findByJobPostAndUser(jobPost, user)
            ?: throw IllegalArgumentException("해당 사용자가 해당 구직 공고에 지원한 내역이 존재하지 않습니다.")
        jobPostApplicationRepository.delete(application)
    }

    fun getApplicantCount(jobPostId: Int): Int =
        jobPostRepository.findById(jobPostId).map { jobPost ->
            jobPostApplicationRepository.findByJobPostJobPostId(jobPost.jobPostId).size
        }.orElseThrow { IllegalArgumentException("해당 구직 공고가 존재하지 않습니다.") }

    fun getApplicantResumes(jobPostId: Int): List<ResumeDto> {
        val jobPost = jobPostService.findById(jobPostId)
            ?: throw IllegalArgumentException("해당 구직 공고가 존재하지 않습니다.")
        val application = jobPostApplicationRepository.findByJobPost(jobPost)

        return application.mapNotNull { application ->
            val user = application.user
            val resume = resumeService.getResumeByUserId(user.userId)
            resume?.let {
                ResumeDto(
                    resumeId = it.resumeId,
                    userId = user.userId,
                    photos = StringConversionUtils.convertStringToList(it.photos ?: " "),
                    createdAt = it.createdAt,
                    experiences = experienceService.getExperienceByResumeId(resume.resumeId)
                        .map { ExperienceDto(it.experienceId, it.description, it.startDate, it.endDate, it.createdAt) },
                    educations = educationService.getEducationByResumeId(resume.resumeId)
                        .map { EducationDto(it.educationId, it.courseName, it.institution, it.completionDate, it.createdAt) },
                    certifications = certificationService.getCertificationByResumeId(resume.resumeId)
                        .map { CertificationDto(it.certificationId, it.name, it.issuedBy, it.issuedDate, it.createdAt) }
                )
            }
        }
    }

    fun getAppliedJobPosts(userId: Int): List<JobPost> {
        val user = userService.findUserById(userId)
            ?: throw IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        val applications = jobPostApplicationRepository.findByUser(user)

        return applications.map { it.jobPost }
    }
}