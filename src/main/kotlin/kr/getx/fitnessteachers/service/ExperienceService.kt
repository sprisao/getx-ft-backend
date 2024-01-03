package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.Experience
import kr.getx.fitnessteachers.repository.ExperienceRepository
import org.springframework.stereotype.Service

@Service
class ExperienceService(private val experienceRepository: ExperienceRepository) {

    fun getAllExperiences(): List<Experience> = experienceRepository.findAll()

    fun addExperience(experience: Experience): Experience = experienceRepository.save(experience)

    fun getExperienceById(id: Int): Experience? = experienceRepository.findById(id).orElse(null)

    fun updateExperience(experience: Experience): Experience = experienceRepository.save(experience)

    fun deleteExperience(id: Int) = experienceRepository.deleteById(id)
}
