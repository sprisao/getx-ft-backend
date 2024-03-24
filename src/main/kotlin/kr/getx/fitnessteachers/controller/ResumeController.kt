package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.ResumeDto
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.service.*
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/resumes")
class ResumeController(
    private val resumeService: ResumeService,
) {

    @GetMapping("/all")
    fun getAllResumes(): ResponseEntity<List<Resume>> = ResponseEntity.ok(resumeService.getAllResumes())

    @PostMapping("/add")
    fun addResume(@RequestBody resumeDto: ResumeDto): ResponseEntity<Resume> = ResponseEntity.ok(resumeService.addResumeWithDetails(resumeDto))

    @GetMapping("/{userId}")
    fun getResumeByUserId(@PathVariable userId: Int): ResponseEntity<ResumeDto> = ResponseEntity.ok(resumeService.getResumeDetailsByUserId(userId))

    @PutMapping("/update/{userId}")
    fun updateResumeByUserId(@PathVariable userId: Int, @RequestBody resumeDto: ResumeDto): ResponseEntity<Resume> = ResponseEntity.ok(resumeService.updateResumeWithDetails(userId, resumeDto))

    @DeleteMapping("/delete/{userId}")
    fun deleteResume(@PathVariable userId: Int): ResponseEntity<String> {
        resumeService.deleteResumeAndRelatedDetails(userId)
        return ResponseEntity.ok("이력서를 성공적으로 삭제하였습니다. 해당 유저 ID : $userId")
    }
}
