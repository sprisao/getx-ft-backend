package kr.getx.fitnessteachers.service

import jakarta.transaction.Transactional
import kr.getx.fitnessteachers.dto.*
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.repository.ResumeRepository
import kr.getx.fitnessteachers.utils.StringConversionUtils
import org.springframework.stereotype.Service
import kr.getx.fitnessteachers.exceptions.ResumeNotFoundException
import kr.getx.fitnessteachers.exceptions.UserNotFoundException

@Service
@Transactional
class ResumeService(
    private val resumeRepository: ResumeRepository,
    private val userService: UserService,
    private val educationService: EducationService,
    private val experienceService: ExperienceService,
    private val certificationService: CertificationService,
) {

    fun getAllResumes(): List<Resume> = resumeRepository.findAll()

    fun findById(resumeId: Int): Resume = resumeRepository.findById(resumeId).orElseThrow { ResumeNotFoundException(resumeId) }

    fun addResumeWithDetails(resumeDto: ResumeDto): Resume {
        val user = userService.findUserById(resumeDto.user.userId)
        val newResume = resumeDto.toEntity(user)

        val saveResume = resumeRepository.save(newResume)

        resumeDto.educations.forEach {
            educationService.addEducationForResume(saveResume, it)
        }
        resumeDto.experiences.forEach {
            experienceService.addExperienceForResume(saveResume, it)
        }
        resumeDto.certifications.forEach {
            certificationService.addCertificationForResume(saveResume, it)
        }
        return saveResume
    }

    fun getResumeDetailsByUserId(userId: Int): ResumeDto {
        val resume = getResumeByUserId(userId)
        return toDto(resume)
    }

    fun updateResumeWithDetails(userId: Int, resumeDto: ResumeDto): Resume {
        val user = userService.findUserById(userId) ?: throw UserNotFoundException(userId)
        val resume = resumeRepository.findByUserUserId(userId) ?: throw ResumeNotFoundException(userId)

        resume.description = resumeDto.description
        resume.photos = StringConversionUtils.convertListToString(resumeDto.photos)

        val updateResume = resumeRepository.save(resume)

        educationService.updateEducation(updateResume, resumeDto.educations.map { it.toEducation(resume)})
        experienceService.updateExperience(updateResume, resumeDto.experiences.map { it.toExperience(resume)})
        certificationService.updateCertification(updateResume, resumeDto.certifications.map { it.toCertification(resume)})

        return updateResume
    }

    fun getResumeByUserId(userId: Int): Resume =
        resumeRepository.findByUserUserId(userId) ?: throw ResumeNotFoundException(userId)

    fun deleteResumeAndRelatedDetails(userId: Int) {
        val resume = resumeRepository.findByUserUserId(userId) ?: throw ResumeNotFoundException(userId)

        educationService.deleteAllByResume(resume)
        experienceService.deleteAllByResume(resume)
        certificationService.deleteAllByResume(resume)

        resumeRepository.delete(resume)
    }

    fun toDto(resume: Resume): ResumeDto {
        val experiences = experienceService.getExperienceByResumeId(resume.resumeId)
           .map { ExperienceDto.fromEntity(it) }
        val educations = educationService.getEducationByResumeId(resume.resumeId)
              .map { EducationDto.fromEntity(it) }
        val certifications = certificationService.getCertificationByResumeId(resume.resumeId)
            .map { CertificationDto.fromEntity(it) }

        return ResumeDto.fromEntity(resume, educations, experiences, certifications)
    }
}