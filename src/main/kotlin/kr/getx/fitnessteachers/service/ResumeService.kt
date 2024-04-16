package kr.getx.fitnessteachers.service

import jakarta.transaction.Transactional
import kr.getx.fitnessteachers.dto.*
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.repository.ResumeRepository
import kr.getx.fitnessteachers.utils.StringConversionUtils
import org.springframework.stereotype.Service
import kr.getx.fitnessteachers.exceptions.ResumeNotFoundException
import kr.getx.fitnessteachers.repository.CertificationRepository
import kr.getx.fitnessteachers.repository.EducationRepository
import kr.getx.fitnessteachers.repository.ExperienceRepository

@Service
@Transactional
class ResumeService(
    private val resumeRepository: ResumeRepository,
    private val userService: UserService,
    private val educationService: EducationService,
    private val experienceService: ExperienceService,
    private val certificationService: CertificationService,
    private val educationRepository: EducationRepository,
    private val experienceRepository: ExperienceRepository,
    private val certificationRepository: CertificationRepository
) {

    fun getAllResumes(): List<Resume> = resumeRepository.findAll()

    fun findById(resumeId: Int): ResumeDto {
        val resume = resumeRepository.findById(resumeId).orElseThrow { ResumeNotFoundException(resumeId) }

        val educations = educationRepository.findAllById(resume.educationIds ?: emptyList())
        val experience = experienceRepository.findAllById(resume.experienceIds ?: emptyList())
        val certifications = certificationRepository.findAllById(resume.certificationIds ?: emptyList())

        val educationDtos = educations.map { EducationDto.fromEntity(it) }
        val experienceDtos = experience.map { ExperienceDto.fromEntity(it) }
        val certificationDtos = certifications.map { CertificationDto.fromEntity(it) }

        return ResumeDto.fromEntity(
            resume,
            educationDtos,
            experienceDtos,
            certificationDtos
        )
    }


    fun addResumeWithDetails(resumeDto: ResumeDto): Resume {
        val user = userService.findUserById(resumeDto.user.userId)
        val newResume = resumeDto.toEntity(user)

        newResume.educationIds = resumeDto.educationIds
        newResume.experienceIds = resumeDto.experienceIds
        newResume.certificationIds = resumeDto.certificationIds

        val saveResume = resumeRepository.save(newResume)
        return saveResume
    }

    fun getResumeDetailsByUserId(userId: Int): List<ResumeDto> {
        val resume = resumeRepository.findAllByUserUserId(userId)
        return resume.map { toDto(it) }
    }

    fun updateResumeWithDetails(resumeDto: ResumeDto): Resume {
        val resume = resumeRepository.findById(resumeDto.resumeId).orElseThrow { ResumeNotFoundException(resumeDto.resumeId) }

        resume.description = resumeDto.description
        resume.photos = StringConversionUtils.convertListToString(resumeDto.photos)
        resume.mainPhoto = resumeDto.mainPhoto
        resume.attachment = resumeDto.attachment
        resume.isDisplay = resumeDto.isDisplay
        resume.isEditing = resumeDto.isEditing
        resume.educationIds = resumeDto.educationIds
        resume.experienceIds = resumeDto.experienceIds
        resume.certificationIds = resumeDto.certificationIds

        val updateResume = resumeRepository.save(resume)
        return updateResume
    }

    fun getResumeByUserId(userId: Int): Resume =
        resumeRepository.findByUserUserId(userId) ?: throw ResumeNotFoundException(userId)

    fun deleteResume(resumeId: Int) {
        val resume = resumeRepository.findById(resumeId).orElseThrow { ResumeNotFoundException(resumeId) }
        resumeRepository.delete(resume)
    }

    fun toDto(resume: Resume): ResumeDto {
        val experiences = experienceService.findExperiencesByIds(resume.experienceIds ?: listOf())
            .map { ExperienceDto.fromEntity(it) }
        val educations = educationService.findEducationsByIds(resume.educationIds ?: listOf())
              .map { EducationDto.fromEntity(it) }
        val certifications = certificationService.findCertificationsByIds(resume.certificationIds ?: listOf())
            .map { CertificationDto.fromEntity(it) }

        return ResumeDto.fromEntity(resume, educations, experiences, certifications)
    }
}