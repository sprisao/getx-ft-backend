package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.Experience
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.repository.ExperienceRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ExperienceService(private val experienceRepository: ExperienceRepository) {

    fun getAllExperiences(): List<Experience> = experienceRepository.findAll()

    fun addExperience(experience: Experience): Experience = experienceRepository.save(experience)

    fun getExperienceById(id: Int): Experience? = experienceRepository.findById(id).orElse(null)

    fun updateExperience(experience: Experience): Experience = experienceRepository.save(experience)

    fun deleteExperience(id: Int) = experienceRepository.deleteById(id)

    fun addExperience(resume: Resume, experience: Experience): ResponseEntity<Map<String, Any>> {
        return try {
            experienceRepository.save(experience)
            ResponseEntity.ok(mapOf("status" to true, "data" to experience))
        } catch (e : Exception) {
            ResponseEntity.badRequest().body(mapOf("status" to false, "data" to e.message as Any))
        }
    }

    fun getExperienceByResumeId(resumeId: Int): List<Experience> {
        return experienceRepository.findByResumeResumeId(resumeId)
    }
}
