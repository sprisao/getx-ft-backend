package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.repository.ResumeRepository
import org.springframework.stereotype.Service

@Service
class ResumeService(private val resumerepository: ResumeRepository) {

    fun getAllResumes(): List<Resume> = ResumeRepository.findAll()

    fun addResume(resume: Resume): Resume = resumerepository.save(resume)

    fun getResumeById(id: int): Resume? = resumerepository.findById(id).orElse(null)

    fun updateResume(resume: Resume): Resume = resumerepository.save(resume)

    fun deleteResume(id: int) = userRepository.deleteById(id)
}