package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.CenterDto
import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.exceptions.CenterNotFoundException
import kr.getx.fitnessteachers.exceptions.CenterOwnershipException
import kr.getx.fitnessteachers.repository.CenterRepository
import kr.getx.fitnessteachers.utils.StringConversionUtils
import org.springframework.stereotype.Service

@Service
class CenterService(
    private val centerRepository: CenterRepository
) {
    fun getAllCenters(): List<Center> = centerRepository.findAll()

    fun findById(centerId: Int): Center? = centerRepository.findById(centerId).orElse(null)

    fun addCenter(center: Center): Center = centerRepository.save(center)

    fun updateCenter(centerId: Int, centerDto: CenterDto): Center =
        centerRepository.findById(centerId).orElseThrow { CenterNotFoundException(centerId) }.apply {
            if (user.userId != centerDto.userId) throw CenterOwnershipException(centerDto.userId, centerId)
            centerName = centerDto.centerName ?: centerName
            photos = StringConversionUtils.convertListToString(centerDto.photos ?: emptyList())
            roadAddress = centerDto.roadAddress ?: roadAddress
            sido = centerDto.sido ?: sido
            sidoEnglish = centerDto.sidoEnglish ?: sidoEnglish
            sigungu = centerDto.sigungu ?: sigungu
            sigunguEnglish = centerDto.sigunguEnglish ?: sigunguEnglish
            description = centerDto.description ?: description
        }.let(centerRepository::save)

    fun deleteCenter(centerId: Int) {
        val center = centerRepository.findByCenterIdAndIsDeletedFalse(centerId).orElseThrow { CenterNotFoundException(centerId) }
        center.isDeleted = true
        centerRepository.save(center)
    }

    fun getCenterByUserId(userId: Int): List<Center> = centerRepository.findByUser_UserId(userId)
}
