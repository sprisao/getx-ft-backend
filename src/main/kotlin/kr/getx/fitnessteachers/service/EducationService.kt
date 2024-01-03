package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.Education
import kr.getx.fitnessteachers.repository.EducationRepository
import org.springframework.stereotype.Service

@Service
class EducationService(private val educationRepository: EducationRepository) {

    fun getAllEducations(): List<Education> = educationRepository.findAll()

    fun addEducation(education: Education): Education = educationRepository.save(education)

    fun getEducationById(id: Int): Education? = educationRepository.findById(id).orElse(null)

    fun updateEducation(education: Education): Education = educationRepository.save(education)

    fun deleteEducation(id: Int) = educationRepository.deleteById(id)
}

