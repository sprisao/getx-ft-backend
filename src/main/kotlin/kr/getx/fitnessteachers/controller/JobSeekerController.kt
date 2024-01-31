package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.JobSeeker
import kr.getx.fitnessteachers.service.JobSeekerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/job-seekers")
class JobSeekerController(private val jobSeekerService: JobSeekerService) {

    @GetMapping
    fun listJobSeekers(): ResponseEntity<List<JobSeeker>> =
        ResponseEntity.ok(jobSeekerService.findAll())

    @GetMapping("/{seekerId}")
    fun getJobSeeker(@PathVariable seekerId: Int): ResponseEntity<JobSeeker> =
        jobSeekerService.findById(seekerId)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PostMapping
    fun createJobSeeker(@RequestBody jobSeeker: JobSeeker): ResponseEntity<JobSeeker> =
        ResponseEntity.ok(jobSeekerService.save(jobSeeker))

    @PutMapping("/{seekerId}")
    fun updateJobSeeker(@PathVariable seekerId: Int, @RequestBody jobSeeker: JobSeeker): ResponseEntity<JobSeeker> =
        jobSeekerService.findById(seekerId)?.let {
            ResponseEntity.ok(jobSeekerService.save(jobSeeker))
        } ?: ResponseEntity.notFound().build()
}
