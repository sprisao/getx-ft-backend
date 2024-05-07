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
    fun addResume(@RequestBody resumeDto: ResumeDto): ResponseEntity<ResumeDto> {
        val savedResume = resumeService.addResumeWithDetails(resumeDto)
        return ResponseEntity.ok(resumeService.toDto(savedResume))
    }

    @GetMapping("/find/{resumeId}")
    fun getResumeById(@PathVariable resumeId: Int): ResponseEntity<ResumeDto> =
        ResponseEntity.ok(resumeService.findById(resumeId))

    @GetMapping("/{userId}")
    fun getResumeByUserId(@PathVariable userId: Int): ResponseEntity<List<ResumeDto>> =
        ResponseEntity.ok(resumeService.getResumeDetailsByUserId(userId))

    @PutMapping("/update")
    fun updateResumeByUserId(@RequestBody resumeDto: ResumeDto): ResponseEntity<ResumeDto> =
        ResponseEntity.ok(resumeService.toDto(resumeService.updateResumeWithDetails(resumeDto)))

    @DeleteMapping("/delete/{resumeId}")
    fun deleteResume(@PathVariable resumeId: Int): ResponseEntity<String> {
        resumeService.deleteResume(resumeId)
        return ResponseEntity.ok("이력서를 삭제하였습니다. 해당 resume ID : $resumeId")
    }
}
