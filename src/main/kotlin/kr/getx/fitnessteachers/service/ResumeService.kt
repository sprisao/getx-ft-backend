package kr.getx.fitnessteachers.service

import jakarta.transaction.Transactional
import kr.getx.fitnessteachers.dto.*
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.repository.ResumeRepository
import kr.getx.fitnessteachers.utils.StringConversionUtils
import org.springframework.stereotype.Service
import kr.getx.fitnessteachers.exceptions.ResumeNotFoundException
import kr.getx.fitnessteachers.exceptions.UserNotFoundException
import org.hibernate.query.IllegalSelectQueryException

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

        newResume.educationIds = resumeDto.educationIds
        newResume.experienceIds = resumeDto.experienceIds
        newResume.certificationIds = resumeDto.certificationIds

        val saveResume = resumeRepository.save(newResume)
        return saveResume
    }

    fun getResumeDetailsByUserId(userId: Int): List<ResumeDto> {
        val resume = resumeRepository.findByResumesUserUserId(userId)
        return resume.map { toDto(it) }
    }

    fun updateResumeWithDetails(userId: Int, resumeDto: ResumeDto): Resume {
        val user = userService.findUserById(userId) ?: throw UserNotFoundException(userId)
        val resume = resumeRepository.findByUserUserId(userId) ?: throw ResumeNotFoundException(userId)

        resume.description = resumeDto.description
        resume.photos = StringConversionUtils.convertListToString(resumeDto.photos)
        resume.mainPhoto = resumeDto.mainPhoto
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

    fun deleteResume(userId: Int, resumeId: Int) {
        val resume = resumeRepository.findByUserUserId(userId) ?: throw ResumeNotFoundException(userId)

        if(userId == resume.user.userId)
            resumeRepository.delete(resume)
        else throw IllegalSelectQueryException("해당 유저 ID로 이력서를 삭제할 수 없습니다.")
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