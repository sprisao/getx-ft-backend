package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.CenterPhoto
import kr.getx.fitnessteachers.service.CenterPhotoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/centerPhotos")
class CenterPhotoController(private val centerPhotoService: CenterPhotoService) {

    @GetMapping
    fun getAllCenterPhotos(): List<CenterPhoto> = centerPhotoService.getAllCenterPhotos()

    @PostMapping("/add")
    fun addCenterPhoto(@RequestBody centerPhoto: CenterPhoto): CenterPhoto = centerPhotoService.addCenterPhoto(centerPhoto)

    @GetMapping("/{id}")
    fun getCenterPhoto(@PathVariable id: Int): CenterPhoto? = centerPhotoService.getCenterPhotoById(id)

    @PutMapping("/update")
    fun updateCenterPhoto(@RequestBody centerPhoto: CenterPhoto): CenterPhoto = centerPhotoService.updateCenterPhoto(centerPhoto)

    @DeleteMapping("/delete/{id}")
    fun deleteCenterPhoto(@PathVariable id: Int) = centerPhotoService.deleteCenterPhoto(id)
}
