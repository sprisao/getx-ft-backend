package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.*
import kr.getx.fitnessteachers.utils.StringConversionUtils
import java.time.LocalDateTime

data class ResumeDto(
        var resumeId : Int,
        var user: UserDto,
        val photos: List<String>,
        val description: String?,
        val isDisplay: Boolean,
        val isEditing: Boolean,
        val experiences: List<ExperienceDto>,
        val educations: List<EducationDto>,
        val certifications: List<CertificationDto>,
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
                        createdAt = this.createdAt ?: LocalDateTime.now()
                )
        }

        companion object {
                fun fromEntity(
                        resume: Resume,
                        educations: List<EducationDto>,
                        experiences: List<ExperienceDto>,
                        certifications: List<CertificationDto>
                ): ResumeDto {
                        return ResumeDto(
                                resumeId = resume.resumeId,
                                user = UserDto.fromEntity(resume.user),
                                photos = StringConversionUtils.convertStringToList(resume.photos ?: " "),
                                isDisplay = resume.isDisplay,
                                isEditing = resume.isEditing,
                                description = resume.description,
                                educations = educations,
                                experiences = experiences,
                                certifications = certifications,
                                createdAt = resume.createdAt
                        )
                }
        }
}
