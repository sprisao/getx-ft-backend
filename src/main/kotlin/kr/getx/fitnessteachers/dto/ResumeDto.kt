package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.*
import kr.getx.fitnessteachers.utils.StringConversionUtils
import java.time.LocalDateTime

data class ResumeDto(
        var resumeId : Int,
        var user: UserDto,
        val photos: List<String>,
        val mainPhoto: String,
        val description: String,
        val isDisplay: Boolean,
        val isEditing: Boolean,
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
        val createdAt : LocalDateTime?
) {
        fun toEntity(user: User): Resume {
                return Resume(
                        resumeId = this.resumeId,
                        user = user,
                        description = this.description,
                        isDisplay = this.isDisplay,
                        isEditing = this.isEditing,
                        photos = StringConversionUtils.convertListToString(this.photos),
                        mainPhoto = this.mainPhoto,
                        educationIds = this.educationIds,
                        experienceIds = this.experienceIds,
                        certificationIds = this.certificationIds,
                        resumePhotoIds = this.resumePhotoIds,
                        resumeAttachmentIds = this.resumeAttachmentIds,
                        isDeleted = this.isDeleted,
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
                                photos = StringConversionUtils.convertStringToList(resume.photos ?: " "),
                                mainPhoto = resume.mainPhoto ?: "",
                                isDisplay = resume.isDisplay ?: true,
                                isEditing = resume.isEditing ?: false,
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
                                createdAt = resume.createdAt
                        )
                }
        }
}
