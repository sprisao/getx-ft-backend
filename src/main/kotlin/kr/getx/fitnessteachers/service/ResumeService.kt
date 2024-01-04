package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.repository.ResumeRepository
import org.springframework.stereotype.Service

@Service
class ResumeService(private val resumeRepository: ResumeRepository) {

    fun getAllResumes(): List<Resume> = resumeRepository.findAll()

    fun addResume(resume: Resume): Resume = resumeRepository.save(resume)

    fun getResumeById(id: Int): Resume? = resumeRepository.findById(id).orElse(null)

    fun updateResume(resume: Resume): Resume = resumeRepository.save(resume)

    fun deleteResume(id: Int) = resumeRepository.deleteById(id)
}