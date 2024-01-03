package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.service.CenterService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/centers")
class CenterController(private val centerService: CenterService) {

    @GetMapping
    fun getAllCenters(): List<Center> = centerService.getAllCenters()

    @PostMapping("/add")
    fun addCenter(@RequestBody center: Center): Center = centerService.addCenter(center)

    @GetMapping("/{id}")
    fun getCenter(@PathVariable id: Int): Center? = centerService.getCenterById(id)

    @PutMapping("/update")
    fun updateCenter(@RequestBody center: Center): Center = centerService.updateCenter(center)

    @DeleteMapping("/delete/{id}")
    fun deleteCenter(@PathVariable id: Int) = centerService.deleteCenter(id)
}
