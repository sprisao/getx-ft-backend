package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.*
import java.time.LocalDateTime

data class ResumeDto(
        var resumeId : Int,
        var user: UserDto,
        val mainPhoto: String,
        val description: String,
        val isDisplay: Boolean,
        val isEditing: Boolean,
        val teacherType: TeacherType,
        val educationIds: List<Int> = listOf(),
        val experienceIds: List<Int> = listOf(),
        val certificationIds: List<Int> = listOf(),
        val resumePhotoIds: List<Int> = listOf(),
        val resumeAttachmentIds: List<Int> = listOf(),
        val educations : List<EducationDto> = listOf(),
        val experiences : List<ExperienceDto> = listOf(),
        val certifications : List<CertificationDto> = listOf(),
        val resumePhotos : List<ResumePhotoDto> = listOf(),
        val resumeAttachments : List<ResumeAttachmentDto> = listOf(),
        val isDeleted: Boolean,
        val isDeletedAt: LocalDateTime?,
        val createdAt : LocalDateTime?
) {
        fun toEntity(user: User): Resume {
                return Resume(
                        resumeId = this.resumeId,
                        user = user,
                        description = this.description,
                        isDisplay = this.isDisplay,
                        isEditing = this.isEditing,
                        teacherType = this.teacherType,
                        mainPhoto = this.mainPhoto,
                        educationIds = this.educationIds,
                        experienceIds = this.experienceIds,
                        certificationIds = this.certificationIds,
                        resumePhotoIds = this.resumePhotoIds,
                        resumeAttachmentIds = this.resumeAttachmentIds,
                        isDeleted = this.isDeleted,
                        isDeletedAt = this.isDeletedAt,
                        createdAt = this.createdAt ?: LocalDateTime.now()
                )
        }

        companion object {
                fun fromEntity(
                        resume: Resume,
                        educations: List<EducationDto>,
                        experiences: List<ExperienceDto>,
                        certifications: List<CertificationDto>,
                        resumePhotos: List<ResumePhotoDto>,
                        resumeAttachments: List<ResumeAttachmentDto>
                ): ResumeDto {
                        return ResumeDto(
                                resumeId = resume.resumeId,
                                user = UserDto.fromEntity(resume.user),
                                mainPhoto = resume.mainPhoto ?: "",
                                isDisplay = resume.isDisplay ?: true,
                                isEditing = resume.isEditing ?: false,
                                teacherType = resume.teacherType ?: TeacherType.FITNESS,
                                description = resume.description ?: " ",
                                educationIds = resume.educationIds ?: listOf(),
                                experienceIds = resume.experienceIds ?: listOf(),
                                certificationIds = resume.certificationIds ?: listOf(),
                                resumePhotoIds = resume.resumePhotoIds ?: listOf(),
                                resumeAttachmentIds = resume.resumeAttachmentIds ?: listOf(),
                                educations = educations,
                                experiences = experiences,
                                certifications = certifications,
                                resumePhotos = resumePhotos,
                                resumeAttachments = resumeAttachments,
                                isDeleted = resume.isDeleted,
                                isDeletedAt = resume.isDeletedAt,
                                createdAt = resume.createdAt
                        )
                }
        }
}
