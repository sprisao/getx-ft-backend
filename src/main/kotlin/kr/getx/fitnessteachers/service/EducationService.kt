package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.EducationDto
import kr.getx.fitnessteachers.entity.Education
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.exceptions.ResumeNotFoundException
import kr.getx.fitnessteachers.repository.EducationRepository
import org.springframework.stereotype.Service

@Service
class EducationService(
    private val educationRepository: EducationRepository,
) {

    fun getAllEducations(): List<Education> = educationRepository.findAll()

    fun getEducationById(id: Int): Education? = educationRepository.findById(id).orElse(null)

    fun addEducation(educationDto: EducationDto, resume: Resume): Education = educationRepository.save(educationDto.toEducation(resume))
    fun addEducationForResume(resume: Resume, educationDto: EducationDto ): Education = educationRepository.save(educationDto.toEducation(resume))

    fun updateEducation(resume: Resume, newEducations: List<Education>) {
        val existingEducations = educationRepository.findByResumeResumeId(resume.resumeId).associateBy { it.educationId }

        // 새 교육 정보를 반복하며 업데이트 또는 추가
        newEducations.forEach { newEdu ->
            val existingEdu = existingEducations[newEdu.educationId]
            educationRepository.save(existingEdu?.apply {
                courseName = newEdu.courseName
                institution = newEdu.institution
                completionDate = newEdu.completionDate
            } ?: newEdu.apply { this.resume = resume })
        }

        // 더 이상 존재하지 않는 교육 정보 삭제
        existingEducations.values.forEach {
            if (newEducations.none { newEdu -> newEdu.educationId == it.educationId}) {
                educationRepository.delete(it)
            }
        }
    }

    fun deleteEducation(id: Int) = educationRepository.deleteById(id)

    fun getEducationByResumeId(resumeId: Int): List<Education> = educationRepository.findByResumeResumeId(resumeId)

    fun deleteAllByResume(resume: Resume) = educationRepository.deleteAllByResume(resume)
}

