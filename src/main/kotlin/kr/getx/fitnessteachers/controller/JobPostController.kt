package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.service.JobPostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/jobPosts")
class JobPostController(private val jobPostService: JobPostService) {

    @GetMapping
    fun getAllJobPosts(): ResponseEntity<List<JobPost>> =
        ResponseEntity.ok(jobPostService.findAll())

    @GetMapping("/{id}")
    fun getJobPostById(@PathVariable id: Int): ResponseEntity<JobPost> =
        jobPostService.findById(id)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PostMapping
    fun createJobPost(@RequestBody jobPost: JobPost): ResponseEntity<JobPost> =
        ResponseEntity.ok(jobPostService.save(jobPost))

    @PutMapping("/{id}")
    fun updateJobPost(@PathVariable id: Int, @RequestBody jobPost: JobPost): ResponseEntity<JobPost> =
        jobPostService.findById(id)?.let {
            ResponseEntity.ok(jobPostService.save(jobPost))
        } ?: ResponseEntity.notFound().build()

    @DeleteMapping("/{id}")
    fun deleteJobPost(@PathVariable id: Int): ResponseEntity<Void> {
        jobPostService.deleteById(id)
        return ResponseEntity.ok().build()
    }
}
