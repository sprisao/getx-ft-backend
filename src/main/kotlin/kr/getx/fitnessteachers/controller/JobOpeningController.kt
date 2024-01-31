package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.JobOpening
import kr.getx.fitnessteachers.service.JobOpeningService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/job-openings")
class JobOpeningController(private val jobOpeningService: JobOpeningService) {

    @GetMapping
    fun getAllJobPosts(): List<JobOpening> = jobOpeningService.getAllJobPosts()

    @PostMapping("/add")
    fun addJobPost(@RequestBody jobOpening: JobOpening): JobOpening = jobOpeningService.addJobPost(jobOpening)

    @GetMapping("/{id}")
    fun getJobPost(@PathVariable id: Int): JobOpening? = jobOpeningService.getJobPostById(id)

    @PutMapping("/update")
    fun updateJobPost(@RequestBody jobOpening: JobOpening): JobOpening = jobOpeningService.updateJobPost(jobOpening)

    @DeleteMapping("/delete/{id}")
    fun deleteJobPost(@PathVariable id: Int) = jobOpeningService.deleteJobPost(id)
}
