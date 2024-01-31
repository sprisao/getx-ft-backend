package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.JobSeeker
import kr.getx.fitnessteachers.repository.JobSeekerRepository
import org.springframework.stereotype.Service

@Service
class JobSeekerService(private val jobSeekerRepository: JobSeekerRepository) {

    fun findAll(): List<JobSeeker> = jobSeekerRepository.findAll()

    fun findById(seekerId: Int): JobSeeker? = jobSeekerRepository.findById(seekerId).orElse(null)

    fun save(jobSeeker: JobSeeker): JobSeeker = jobSeekerRepository.save(jobSeeker)

    fun deleteById(seekerId: Int) = jobSeekerRepository.deleteById(seekerId)
}
