package kr.getx.fitnessteachers.service

import jakarta.transaction.Transactional
import kr.getx.fitnessteachers.dto.*
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.utils.StringConversionUtils
import org.springframework.stereotype.Service
import kr.getx.fitnessteachers.exceptions.ResumeNotFoundException
import kr.getx.fitnessteachers.repository.*

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
    private val certificationRepository: CertificationRepository,
    private val resumePhotoRepository: ResumePhotoRepository,
    private val resumeAttachmentRepository: ResumeAttachmentRepository,
    private val resumePhotoService: ResumePhotoService,
    private val resumeAttachmentService: ResumeAttachmentService
) {

    fun getAllResumes(): List<Resume> = resumeRepository.findAll()

    fun getResumesByResumeIds(resumeIds: List<Int>): List<Resume> {
        return resumeRepository.findAllByResumeIdIn(resumeIds)
    }

    fun findById(resumeId: Int): ResumeDto {
        val resume = resumeRepository.findById(resumeId).orElseThrow { ResumeNotFoundException(resumeId) }

        val educations = educationRepository.findAllById(resume.educationIds ?: emptyList())
        val experience = experienceRepository.findAllById(resume.experienceIds ?: emptyList())
        val certifications = certificationRepository.findAllById(resume.certificationIds ?: emptyList())
        val resumePhotos = resumePhotoRepository.findAllById(resume.resumePhotoIds ?: emptyList())
        val resumeAttachments = resumeAttachmentRepository.findAllById(resume.resumeAttachmentIds ?: emptyList())

        val educationDtos = educations.map { EducationDto.fromEntity(it) }
        val experienceDtos = experience.map { ExperienceDto.fromEntity(it) }
        val certificationDtos = certifications.map { CertificationDto.fromEntity(it) }
        val resumePhotoDtos = resumePhotos.map { ResumePhotoDto.fromEntity(it) }
        val resumeAttachmentDtos = resumeAttachments.map { ResumeAttachmentDto.fromEntity(it) }

        return ResumeDto.fromEntity(
            resume,
            educationDtos,
            experienceDtos,
            certificationDtos,
            resumePhotoDtos,
            resumeAttachmentDtos
        )
    }


    fun addResumeWithDetails(resumeDto: ResumeDto): Resume {
        val user = userService.findUserById(resumeDto.user.userId)
        val newResume = resumeDto.toEntity(user)

        newResume.educationIds = resumeDto.educationIds
        newResume.experienceIds = resumeDto.experienceIds
        newResume.certificationIds = resumeDto.certificationIds
        newResume.resumePhotoIds = resumeDto.resumePhotoIds
        newResume.resumeAttachmentIds = resumeDto.resumeAttachmentIds

        val saveResume = resumeRepository.save(newResume)
        return saveResume
    }

    fun getResumeDetailsByUserId(userId: Int): List<ResumeDto> {
        val resume = resumeRepository.findAllByUserUserId(userId)
        return resume.map { toDto(it) }
    }

    fun updateResumeWithDetails(resumeDto: ResumeDto): Resume {
        val resume =
            resumeRepository.findById(resumeDto.resumeId).orElseThrow { ResumeNotFoundException(resumeDto.resumeId) }

        resume.description = resumeDto.description
        resume.photos = StringConversionUtils.convertListToString(resumeDto.photos)
        resume.mainPhoto = resumeDto.mainPhoto
        resume.isDisplay = resumeDto.isDisplay
        resume.isEditing = resumeDto.isEditing
        resume.educationIds = resumeDto.educationIds
        resume.experienceIds = resumeDto.experienceIds
        resume.certificationIds = resumeDto.certificationIds
        resume.resumePhotoIds = resumeDto.resumePhotoIds
        resume.resumeAttachmentIds = resumeDto.resumeAttachmentIds
        resume.isDeleted = resumeDto.isDeleted

        val updateResume = resumeRepository.save(resume)
        return updateResume
    }

    fun deleteResume(resumeId: Int) {
        val resume =
            resumeRepository.findByResumeIdAndDeletedFalse(resumeId).orElseThrow { ResumeNotFoundException(resumeId) }
        resume.isDeleted = true
        resumeRepository.save(resume)
    }

    fun toDto(resume: Resume): ResumeDto {
        val experiences = experienceService.findExperiencesByIds(resume.experienceIds ?: listOf())
            .map { ExperienceDto.fromEntity(it) }
        val educations = educationService.findEducationsByIds(resume.educationIds ?: listOf())
            .map { EducationDto.fromEntity(it) }
        val certifications = certificationService.findCertificationsByIds(resume.certificationIds ?: listOf())
            .map { CertificationDto.fromEntity(it) }
        val resumePhotos = resumePhotoService.findResumePhotosByIds(resume.resumePhotoIds ?: listOf())
            .map { ResumePhotoDto.fromEntity(it) }
        val resumeAttachments = resumeAttachmentService.findResumeAttachmentById(resume.resumeAttachmentIds ?: listOf())
            .map { ResumeAttachmentDto.fromEntity(it) }

        return ResumeDto.fromEntity(resume, educations, experiences, certifications, resumePhotos, resumeAttachments)
    }
}