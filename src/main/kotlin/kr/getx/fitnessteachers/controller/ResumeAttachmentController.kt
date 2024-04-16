package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.ResumeAttachmentDto
import kr.getx.fitnessteachers.service.ResumeAttachmentService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/resumeAttachments")
class ResumeAttachmentController (
    private val resumeAttachmentService: ResumeAttachmentService
){
        @GetMapping("/all")
        fun getAllResumeAttachments(): ResponseEntity<List<ResumeAttachmentDto>> =
            ResponseEntity.ok(resumeAttachmentService.getAllResumeAttachments().map(ResumeAttachmentDto::fromEntity))

        @GetMapping("/{resumeAttachmentId}")
        fun findResumeAttachmentById(@PathVariable resumeAttachmentId: Int): ResponseEntity<ResumeAttachmentDto> =
            ResponseEntity.ok(ResumeAttachmentDto.fromEntity(resumeAttachmentService.findResumeAttachmentById(resumeAttachmentId)))

        @PostMapping("/add")
        fun addResumeAttachment(@RequestBody resumeAttachmentDto: ResumeAttachmentDto): ResponseEntity<ResumeAttachmentDto> =
            ResponseEntity.ok(ResumeAttachmentDto.fromEntity(resumeAttachmentService.addResumeAttachment(resumeAttachmentDto)))

        @PutMapping("/update/{resumeAttachmentId}")
        fun updateResumeAttachment(@PathVariable resumeAttachmentId: Int, @RequestBody resumeAttachmentDto: ResumeAttachmentDto): ResponseEntity<ResumeAttachmentDto> =
            ResponseEntity.ok(ResumeAttachmentDto.fromEntity(resumeAttachmentService.updateResumeAttachment(resumeAttachmentId, resumeAttachmentDto)))

        @DeleteMapping("/delete/{resumeAttachmentId}")
        fun deleteResumeAttachment(@PathVariable resumeAttachmentId: Int): ResponseEntity<Void> {
            resumeAttachmentService.deleteResumeAttachment(resumeAttachmentId)
            return ResponseEntity.noContent().build()
        }
}