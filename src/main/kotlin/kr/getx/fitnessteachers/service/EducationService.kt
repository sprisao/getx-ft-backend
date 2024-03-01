package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.Education
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.repository.EducationRepository
import org.springframework.stereotype.Service
import org.springframework.http.ResponseEntity

@Service
class EducationService(private val educationRepository: EducationRepository) {

    fun getAllEducations(): List<Education> = educationRepository.findAll()

    fun addEducation(education: Education): Education = educationRepository.save(education)

    fun getEducationById(id: Int): Education? = educationRepository.findById(id).orElse(null)

    fun updateEducation(resume: Resume, education: Education) {
      val existingEducation = educationRepository.findByResumeResumeId(resume.resumeId)

        existingEducation.forEach { existingEducation ->
            if(existingEducation.educationId == education.educationId) {
                existingEducation.apply {
                    this.courseName = education.courseName
                    this.institution = education.institution
                    this.completionDate = education.completionDate
                }
                educationRepository.save(existingEducation)
            }
        }
    }

    fun deleteEducation(id: Int) = educationRepository.deleteById(id)

    fun addEducation(resume: Resume, education: Education): ResponseEntity<Map<String, Any>> {
       return try {
           educationRepository.save(education)
           ResponseEntity.ok(mapOf("status" to true, "data" to education))
       } catch (e : Exception) {
           ResponseEntity.badRequest().body(mapOf("status" to false, "data" to e.message as Any))
       }
    }

    fun getEducationByResumeId(resumeId: Int): List<Education> {
        return educationRepository.findByResumeResumeId(resumeId)
    }

    fun deleteAllByResume(resume: Resume) {
        educationRepository.deleteAllByResume(resume)
    }
}

