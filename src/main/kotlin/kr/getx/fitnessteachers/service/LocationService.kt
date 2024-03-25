package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.repository.LocationRepository
import kr.getx.fitnessteachers.entity.Location
import org.springframework.stereotype.Service

@Service
class LocationService(
    private val locationRepository: LocationRepository
) {

    fun getProvince(): List<Location> = locationRepository.findById(0).orElseThrow().children

    fun getCities(provinceId: Int): List<Location> {
        val province = locationRepository.findById(provinceId).orElseThrow()
        return if (province != null) {
            locationRepository.findCitiesByParent(province)
        } else {
            emptyList()
        }
    }
}