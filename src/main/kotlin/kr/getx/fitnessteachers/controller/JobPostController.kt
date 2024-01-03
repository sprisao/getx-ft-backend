package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.service.JobPostService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/jobPosts")
class JobPostController(private val jobPostService: JobPostService) {

    @GetMapping
    fun getAllJobPosts(): List<JobPost> = jobPostService.getAllJobPosts()

    @PostMapping("/add")
    fun addJobPost(@RequestBody jobPost: JobPost): JobPost = jobPostService.addJobPost(jobPost)

    @GetMapping("/{id}")
    fun getJobPost(@PathVariable id: Int): JobPost? = jobPostService.getJobPostById(id)

    @PutMapping("/update")
    fun updateJobPost(@RequestBody jobPost: JobPost): JobPost = jobPostService.updateJobPost(jobPost)

    @DeleteMapping("/delete/{id}")
    fun deleteJobPost(@PathVariable id: Int) = jobPostService.deleteJobPost(id)
}
