package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.UpdateCenterDto
import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.repository.CenterRepository
import kr.getx.fitnessteachers.utils.StringConversionUtils
import org.springframework.stereotype.Service
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Page

@Service
class CenterService(private val centerRepository: CenterRepository) {

    fun getAllCenters(): List<Center> = centerRepository.findAll()

    fun findById(centerId: Int): Center? = centerRepository.findById(centerId).orElse(null)

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

    fun getCenterByUserId(userId: Int): List<Center> {
        return centerRepository.findByUser_UserId(userId)
    }

    // 센터 검색 기능
    fun searchCenters(keyword: String?, locationProvince: String?, locationCity: String?, pageable: Pageable): Page<Center> {
        return centerRepository.findByCriteria(keyword, locationProvince, locationCity, pageable)
    }
}
