package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.CenterPhoto
import kr.getx.fitnessteachers.repository.CenterPhotoRepository
import org.springframework.stereotype.Service

@Service
class CenterPhotoService(private val centerPhotoRepository: CenterPhotoRepository) {

    fun getAllCenterPhotos(): List<CenterPhoto> = centerPhotoRepository.findAll()

    fun addCenterPhoto(centerPhoto: CenterPhoto): CenterPhoto = centerPhotoRepository.save(centerPhoto)

    fun getCenterPhotoById(id: Int): CenterPhoto? = centerPhotoRepository.findById(id).orElse(null)

    fun updateCenterPhoto(centerPhoto: CenterPhoto): CenterPhoto = centerPhotoRepository.save(centerPhoto)

    fun deleteCenterPhoto(id: Int) = centerPhotoRepository.deleteById(id)
}
