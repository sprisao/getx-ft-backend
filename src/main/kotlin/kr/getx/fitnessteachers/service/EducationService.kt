package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.Education
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.repository.EducationRepository
import org.springframework.stereotype.Service
import org.springframework.http.ResponseEntity

@Service
class EducationService(private val educationRepository: EducationRepository) {

    fun getAllEducations(): List<Education> = educationRepository.findAll()

    fun getEducationById(id: Int): Education? = educationRepository.findById(id).orElse(null)

    fun updateEducation(resume: Resume, newEducation: List<Education>) {
      val existingEducation = educationRepository.findByResumeResumeId(resume.resumeId)

      newEducation.forEach { newEducation ->
          val existingEducation = existingEducation.find { it.educationId == newEducation.educationId }
          // 업데이트
          if(existingEducation != null) {
              existingEducation.courseName = newEducation.courseName
              existingEducation.institution = newEducation.institution
              existingEducation.completionDate = newEducation.completionDate
              educationRepository.save(existingEducation)
          } else {
          // 추가
              educationRepository.save(newEducation.apply { this.resume = resume})
          }
      }

      // 삭제
      val newEducationIds = newEducation.mapNotNull { it.educationId }
      existingEducation.forEach {
          if(it.educationId !in newEducationIds) {
              educationRepository.delete(it)
          }
      }
    }

    fun deleteEducation(id: Int) = educationRepository.deleteById(id)

    fun addEducation(resume: Resume, education: Education ): ResponseEntity<Map<String, Any>> {
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

