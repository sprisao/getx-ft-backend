package kr.getx.fitnessteachers.service

import java.time.LocalDateTime
import kr.getx.fitnessteachers.repository.*
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SchedulerService(
    private val centerRepository: CenterRepository,
    private val certificationRepository: CertificationRepository,
    private val educationRepository: EducationRepository,
    private val experienceRepository: ExperienceRepository,
    private val jobPostRepository: JobPostRepository,
    private val resumeAttachmentRepository: ResumeAttachmentRepository,
    private val resumePhotoRepository: ResumePhotoRepository,
    private val resumeRepository: ResumeRepository,
    private val userRepository: UserRepository,
) {

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    fun deleteExpiredRecords() {
        val expiredDateTime = LocalDateTime.now().minusDays(90)

        // Center 엔티티의 삭제된 레코드 삭제
        val expiredCenters = centerRepository.findByIsDeletedTrueAndIsDeletedAtBefore(expiredDateTime)
        centerRepository.deleteAll(expiredCenters)

        // Certification 엔티티의 삭제된 레코드 삭제
        val expiredCertifications =
            certificationRepository.findByIsDeletedTrueAndIsDeletedAtBefore(expiredDateTime)
        certificationRepository.deleteAll(expiredCertifications)

        // Education 엔티티의 삭제된 레코드 삭제
        val expiredEducations =
            educationRepository.findByIsDeletedTrueAndIsDeletedAtBefore(expiredDateTime)
        educationRepository.deleteAll(expiredEducations)

        // Experience 엔티티의 삭제된 레코드 삭제
        val expiredExperiences =
            experienceRepository.findByIsDeletedTrueAndIsDeletedAtBefore(expiredDateTime)
        experienceRepository.deleteAll(expiredExperiences)

        // JobPost 엔티티의 삭제된 레코드 삭제
        val expiredJobPosts =
            jobPostRepository.findByIsDeletedTrueAndIsDeletedAtBefore(expiredDateTime)
        jobPostRepository.deleteAll(expiredJobPosts)

        // ResumeAttachment 엔티티의 삭제된 레코드 삭제
        val expiredResumeAttachments =
            resumeAttachmentRepository.findByIsDeletedTrueAndIsDeletedAtBefore(expiredDateTime)
        resumeAttachmentRepository.deleteAll(expiredResumeAttachments)

        // ResumePhoto 엔티티의 삭제된 레코드 삭제
        val expiredResumePhotos =
            resumePhotoRepository.findByIsDeletedTrueAndIsDeletedAtBefore(expiredDateTime)
        resumePhotoRepository.deleteAll(expiredResumePhotos)

        // Resume 엔티티의 삭제된 레코드 삭제
        val expiredResumes = resumeRepository.findByIsDeletedTrueAndIsDeletedAtBefore(expiredDateTime)
        resumeRepository.deleteAll(expiredResumes)

        // User 엔티티의 삭제된 레코드 삭제
        val expiredUsers = userRepository.findByIsDeletedTrueAndIsDeletedAtBefore(expiredDateTime)
        userRepository.deleteAll(expiredUsers)
    }
}
