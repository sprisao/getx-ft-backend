package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.service.LocationService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import kr.getx.fitnessteachers.entity.Location
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@RestController
@RequestMapping("/locations")
class LocationController(
    private val locationService: LocationService
) {

    @GetMapping("/provinces")
    fun getProvinces() : ResponseEntity<List<Location>> = ResponseEntity.ok(locationService.getProvince())

    @GetMapping("/cities/{provinceId}")
    fun getCities(@RequestParam provinceId: Int) : ResponseEntity<List<Location>> = ResponseEntity.ok(locationService.getCities(provinceId))
}