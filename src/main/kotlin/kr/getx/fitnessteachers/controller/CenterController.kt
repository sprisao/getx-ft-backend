package kr.getx.fitnessteachers.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import kr.getx.fitnessteachers.dto.CenterDto
import kr.getx.fitnessteachers.dto.UpdateCenterDto
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
    fun addCenter(@RequestBody centerDto: CenterDto, request: HttpServletRequest): ResponseEntity<Any> {
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

    @GetMapping("/{userId}")
    fun getCenterByUserId(@PathVariable userId: Int): ResponseEntity<Any> {
        val user = userService.findUserById(userId)

        return if (user!= null) {
            val centers = centerService.getCenterByUserId(user.userId).map { center ->
                CenterDto(
                    centerId = center.centerId,
                    centerName = center.centerName,
                    photos = center.photos?.let { StringConversionUtils.convertStringToList(it) } ?: emptyList(),
                    locationProvince = center.locationProvince,
                    locationCity = center.locationCity,
                    description = center.description,
                    userId = center.user!!.userId
                )
            }

            if (centers.isEmpty()) {
                return ResponseEntity.ok().body("등록된 센터가 없습니다.")
            } else {
            return ResponseEntity.ok().body(centers)
            }
        } else {
            ResponseEntity.ok().body("해당 유저가 존재하지 않습니다.")
        }
    }

    @PutMapping("/update/{centerId}")
    fun updateCenter(@PathVariable centerId: Int,@RequestBody @Valid updateCenterDto: UpdateCenterDto): ResponseEntity<Any>{
        val updateCenter = centerService.updateCenter(centerId, updateCenterDto)
        return ResponseEntity.ok(updateCenter)
    }
    @DeleteMapping("/delete/{centerId}")
    fun deleteCenter(@PathVariable centerId: Int): ResponseEntity<Any>{
        centerService.deleteCenter(centerId)
        return ResponseEntity.ok().body("센터가 성공적으로 삭제되었습니다.")
    }
}
