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

    @GetMapping("/user/{userId}")
    fun findResumeAttachmentByUserIds(@PathVariable userId: Int): ResponseEntity<List<ResumeAttachmentDto>> =
        ResponseEntity.ok(resumeAttachmentService.findResumeAttachmentByUserIds(userId).map(ResumeAttachmentDto::fromEntity))

    @PostMapping("/sync")
    fun syncResumeAttachment(@RequestBody resumeAttachmentDto: List<ResumeAttachmentDto>): ResponseEntity<List<ResumeAttachmentDto>> =
        ResponseEntity.ok(resumeAttachmentService.syncResumeAttachment(resumeAttachmentDto).map(ResumeAttachmentDto::fromEntity))

    @PutMapping ("/update")
    fun updateResumeAttachment(@RequestBody resumeAttachmentDto: List<ResumeAttachmentDto>): ResponseEntity<List<ResumeAttachmentDto>> =
        ResponseEntity.ok(resumeAttachmentService.updateResumeAttachment(resumeAttachmentDto).map(ResumeAttachmentDto::fromEntity))

    @DeleteMapping("/delete")
    fun deleteResumeAttachment(@RequestBody resumeAttachmentId: List<Int>): ResponseEntity<Void> {
        resumeAttachmentService.deleteResumeAttachment(resumeAttachmentId)
        return ResponseEntity.noContent().build()
    }
}