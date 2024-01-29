package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.repository.CenterRepository
import org.springframework.stereotype.Service

@Service
class CenterService(private val centerRepository: CenterRepository) {

    fun getAllCenters(): List<Center> = centerRepository.findAll()

    fun addCenter(center: Center): Center = centerRepository.save(center)

    fun getCenterById(id: Int): Center? = centerRepository.findById(id).orElse(null)

    fun updateCenter(center: Center): Center = centerRepository.save(center)

    fun deleteCenter(id: Int) = centerRepository.deleteById(id)
}
