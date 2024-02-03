package kr.getx.fitnessteachers.controller

import jakarta.persistence.Id
import jakarta.servlet.http.HttpServletRequest
import kr.getx.fitnessteachers.dto.CenterData
import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.service.CenterService
import kr.getx.fitnessteachers.service.UserService
import kr.getx.fitnessteachers.utils.StringConversionUtils
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
@RestController
@RequestMapping("/api/centers")
class CenterController(private val centerService: CenterService, private val userService: UserService) {

    @GetMapping
    fun getAllCenters(): List<Center> = centerService.getAllCenters()

    @PostMapping("/add")
    fun addCenter(@RequestBody centerData: CenterData, request: HttpServletRequest): ResponseEntity<Center> {
        val user = userService.findUserById(centerData.userId)

        val photoString = StringConversionUtils.convertListToString(centerData.photos)
        val center = Center(
                user = user,
                centerName = centerData.centerName,
                photos = photoString,
                locationProvince = centerData.locationProvince,
                locationCity = centerData.locationCity,
                description = centerData.description
        )

        val saveCenter = centerService.addCenter(center)
        return ResponseEntity.ok(saveCenter)
    }

    @GetMapping("/user/{userId}")
    fun getCenterByUserId(@PathVariable userId: Int): ResponseEntity<List<CenterData>> {
        val user = userService.findUserById(userId)
        if (user!= null) {
            val centers = centerService.getCenterByUserId(user.userId).map { center ->
                CenterData(
                        centerName = center.centerName,
                        photos = center.photos?.let { StringConversionUtils.convertStringToList(it) } ?: emptyList(),
                        locationCity = center.locationCity,
                        description = center.description,
                        userId = center.user!!.userId
                )
            }
            return ResponseEntity.ok().body(centers)
        } else {
            return ResponseEntity.notFound().build()
        }
    }


    @GetMapping("/{id}")
    fun getCenter(@PathVariable id: Int): Center? = centerService.getCenterById(id)

    @PutMapping("/update")
    fun updateCenter(@RequestBody center: Center): Center = centerService.updateCenter(center)

    @DeleteMapping("/delete/{id}")
    fun deleteCenter(@PathVariable id: Int) = centerService.deleteCenter(id)
}
