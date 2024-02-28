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

    fun addResume(resume: Resume): Resume = resumeRepository.save(resume)

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

    fun getResumeById(id: Int): Resume? = resumeRepository.findById(id).orElse(null)

    fun updateResume(resume: Resume): Resume = resumeRepository.save(resume)

    fun deleteResume(id: Int) = resumeRepository.deleteById(id)

    fun getResumeByUserId(userId: Int): Resume? {
        return resumeRepository.findByUserUserId(userId)
    }
}