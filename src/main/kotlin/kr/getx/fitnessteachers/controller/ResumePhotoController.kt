package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.ResumePhoto
import kr.getx.fitnessteachers.service.ResumePhotoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/resumePhotos")
class ResumePhotoController(private val resumePhotoService: ResumePhotoService) {

    @GetMapping
    fun getAllResumePhotos(): List<ResumePhoto> = resumePhotoService.getAllResumePhotos()

    @PostMapping("/add")
    fun addResumePhoto(@RequestBody resumePhoto: ResumePhoto): ResumePhoto = resumePhotoService.addResumePhoto(resumePhoto)

    @GetMapping("/{id}")
    fun getResumePhoto(@PathVariable id: Int): ResumePhoto? = resumePhotoService.getResumePhotoById(id)

    @PutMapping("/update")
    fun updateResumePhoto(@RequestBody resumePhoto: ResumePhoto): ResumePhoto = resumePhotoService.updateResumePhoto(resumePhoto)
}