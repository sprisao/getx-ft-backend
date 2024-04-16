package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.service.ResumePhotoService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import kr.getx.fitnessteachers.dto.ResumePhotoDto


@RestController
@RequestMapping("/api/resumePhotos")
class ResumePhotoController(
    private val resumePhotoService: ResumePhotoService
){
    @GetMapping("/all")
    fun getAllResumePhotos(): ResponseEntity<List<ResumePhotoDto>> =
        ResponseEntity.ok(resumePhotoService.getAllResumePhotos().map(ResumePhotoDto::fromEntity))

    @GetMapping("/user/{userId}")
    fun findResumePhotosByUserIds(@PathVariable userId: Int): ResponseEntity<List<ResumePhotoDto>> =
        ResponseEntity.ok(resumePhotoService.findResumePhotosByUserIds(userId).map(ResumePhotoDto::fromEntity))

    @PostMapping("/sync")
    fun syncResumePhotos(@RequestBody resumePhotoDto: List<ResumePhotoDto>): ResponseEntity<List<ResumePhotoDto>> =
        ResponseEntity.ok(resumePhotoService.syncResumePhotos(resumePhotoDto).map(ResumePhotoDto::fromEntity))

    @PutMapping("/update")
    fun updateResumePhotos(@RequestBody resumePhotoDtos: List<ResumePhotoDto>): ResponseEntity<List<ResumePhotoDto>> =
        ResponseEntity.ok(resumePhotoService.updateResumePhotos(resumePhotoDtos).map(ResumePhotoDto::fromEntity))

    @DeleteMapping("/delete")
    fun deleteResumePhotos(@RequestBody resumePhotoIds: List<Int>): ResponseEntity<Void> {
        resumePhotoService.deleteResumePhotos(resumePhotoIds)
        return ResponseEntity.noContent().build()
    }
}