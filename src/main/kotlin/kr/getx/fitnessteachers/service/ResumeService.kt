package kr.getx.fitnessteachers.service

import jakarta.transaction.Transactional
import kr.getx.fitnessteachers.dto.ResumeDto
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.repository.ResumeRepository
import kr.getx.fitnessteachers.utils.StringConversionUtils
import org.springframework.stereotype.Service

@Service
@Transactional
class ResumeService(
    private val resumeRepository: ResumeRepository,
    private val userService: UserService,
    private val educationService: EducationService,
    private val experienceService: ExperienceService,
    private val certificationService: CertificationService
) {

    fun getAllResumes(): List<Resume> = resumeRepository.findAll()

    fun addResumeWithDetails(resumeDto: ResumeDto): Resume {
        val user = userService.findUserById(resumeDto.userId) ?: throw Exception("User not found")

        val photoString = StringConversionUtils.convertListToString(resumeDto.photos)

        val resume = resumeRepository.save(resumeDto.toResume(user, photoString))

        resumeDto.educations.forEach { educationDto ->
            educationService.addEducation(resume, educationDto.toEducation(resume))
        }

        resumeDto.experiences.forEach { experienceDto ->
            experienceService.addExperience(resume, experienceDto.toExperience(resume))
        }

        resumeDto.certifications.forEach { certificationDto ->
            certificationService.addCertification(resume, certificationDto.toCertification(resume))
        }
        return resume
    }

    fun updateResumeWithDetails(resumeDto: ResumeDto): Resume {
        // Resume ID를 통해 기존 이력서를 찾습니다.
        val resume = resumeDto.userId?.let {
            resumeRepository.findByUserUserId(it) ?: throw Exception("Resume not found")
        } ?: throw Exception("Resume ID is required for update operation.")

        // 유저 정보 업데이트 (필요한 경우)
        val user = userService.findUserById(resumeDto.userId) ?: throw Exception("User not found")


        // 사진 정보 업데이트
        val photoString = StringConversionUtils.convertListToString(resumeDto.photos)
        resume.photos = photoString

        // 이력서 업데이트
        resumeRepository.save(resume)

        resumeDto.educations.forEach { educationDto ->
            educationService.updateEducation(resume, educationDto.toEducation(resume))
        }

        resumeDto.experiences.forEach { experienceDto ->
            experienceService.updateExperience(resume, experienceDto.toExperience(resume))
        }

        resumeDto.certifications.forEach { certificationDto ->
            certificationService.updateCertification(resume, certificationDto.toCertification(resume))
        }
        return resume
    }

    fun getResumeByUserId(userId: Int): Resume? {
        return resumeRepository.findByUserUserId(userId)
    }

    fun deleteResumeAndRelatedDetails(userId: Int) {
        val resume = resumeRepository.findByUserUserId(userId) ?: throw Exception("Resume not found")

        educationService.deleteAllByResume(resume)
        experienceService.deleteAllByResume(resume)
        certificationService.deleteAllByResume(resume)

        resumeRepository.delete(resume)
    }
}