package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.ExperienceDto
import kr.getx.fitnessteachers.entity.Experience
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.repository.ExperienceRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ExperienceService(
    private val experienceRepository: ExperienceRepository,
    private val resumeService: ResumeService
) {

    fun getAllExperiences(): List<Experience> = experienceRepository.findAll()
    fun getExperienceById(id: Int): Experience? = experienceRepository.findById(id).orElse(null)
    fun addExperience(experienceDto: ExperienceDto, resumeId: Int): Experience {
        val resume = resumeService.findByResumeId(resumeId) ?: throw Exception("해당 이력서를 찾을 수 없습니다.")
        return experienceRepository.save(experienceDto.toExperience(resume))
    }
    fun addExperienceForResume(resume: Resume, experienceDto: ExperienceDto): Experience = experienceRepository.save(experienceDto.toExperience(resume))

    fun updateExperience(resume: Resume, newExperience: List<Experience>) {
        val existingExperiences = experienceRepository.findByResumeResumeId(resume.resumeId).associateBy {it.experienceId}

        // 새 경험 정보를 반복하며 업데이트 또는 추가
        newExperience.forEach { newExp ->
            val existingExp = existingExperiences[newExp.experienceId]
            experienceRepository.save(existingExp?.apply {
                description = newExp.description
                startDate = newExp.startDate
                endDate = newExp.endDate
            } ?: newExp.apply { this.resume = resume })
        }

        // 더 이상 존재하지 않는 경험 정보 삭제
        existingExperiences.values.forEach {
            if (newExperience.none { newExp -> newExp.experienceId == it.experienceId }) {
                experienceRepository.delete(it)
            }
        }
    }

    fun deleteExperience(id: Int) = experienceRepository.deleteById(id)

    fun getExperienceByResumeId(resumeId: Int): List<Experience> = experienceRepository.findByResumeResumeId(resumeId)

    fun deleteAllByResume(resume: Resume) = experienceRepository.deleteAllByResume(resume)
}
