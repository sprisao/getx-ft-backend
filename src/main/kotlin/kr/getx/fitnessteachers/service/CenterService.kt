package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.UpdateCenterDto
import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.repository.CenterRepository
import kr.getx.fitnessteachers.utils.StringConversionUtils
import org.springframework.stereotype.Service

@Service
class CenterService(private val centerRepository: CenterRepository) {

    fun getAllCenters(): List<Center> = centerRepository.findAll()

    fun addCenter(center: Center): Center = centerRepository.save(center)

    fun updateCenter(centerId: Int, updateCenterDto: UpdateCenterDto): Center {
        val center = centerRepository.findById(centerId).orElseThrow{ throw Exception("해당 센터가 존재하지 않습니다.") }

        center.apply {
            centerName = updateCenterDto.centerName ?: centerName
            photos = updateCenterDto.photos.let { StringConversionUtils.convertListToString(it) }
            locationProvince = updateCenterDto.locationProvince ?: locationProvince
            locationCity = updateCenterDto.locationCity ?: locationCity
            description = updateCenterDto.description ?: description
        }

        return centerRepository.save(center)
    }

    fun deleteCenter(centerId: Int) {
        val center = centerRepository.findById(centerId)
        if(center.isPresent) {
            centerRepository.delete(center.get())
        } else {
            throw Exception("해당 센터가 존재하지 않습니다.")
        }
    }

    fun getCenterByUserId(userId: Int): List<Center> = centerRepository.findByUser_UserId(userId)
}
