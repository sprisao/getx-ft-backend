package kr.getx.fitnessteachers.controller

import jakarta.servlet.http.HttpServletRequest
import kr.getx.fitnessteachers.dto.CenterDto
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
    fun addCenter(@RequestBody centerDto: CenterDto, request: HttpServletRequest): ResponseEntity<Center> {
        val user = userService.findUserById(centerDto.userId)

        val photoString = StringConversionUtils.convertListToString(centerDto.photos)
        val center = Center(
                user = user,
                centerName = centerDto.centerName,
                photos = photoString,
                locationProvince = centerDto.locationProvince,
                locationCity = centerDto.locationCity,
                description = centerDto.description
        )

        val saveCenter = centerService.addCenter(center)
        return ResponseEntity.ok(saveCenter)
    }

    @GetMapping("/user/{userId}")
    fun getCenterByUserId(@PathVariable userId: Int): ResponseEntity<List<CenterDto>> {
        val user = userService.findUserById(userId)
        if (user!= null) {
            val centers = centerService.getCenterByUserId(user.userId).map { center ->
                CenterDto(
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
