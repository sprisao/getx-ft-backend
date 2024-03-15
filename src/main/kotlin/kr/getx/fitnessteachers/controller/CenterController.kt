package kr.getx.fitnessteachers.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import kr.getx.fitnessteachers.dto.CenterDto
import kr.getx.fitnessteachers.dto.UpdateCenterDto
import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.exceptions.CenterNotFoundException
import kr.getx.fitnessteachers.exceptions.CenterOwnershipException
import kr.getx.fitnessteachers.exceptions.UserNotFoundException
import kr.getx.fitnessteachers.service.CenterService
import kr.getx.fitnessteachers.service.UserService
import kr.getx.fitnessteachers.utils.StringConversionUtils
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Page
@RestController
@RequestMapping("/api/centers")
class CenterController(
    private val centerService: CenterService,
    private val userService: UserService
) {

    @GetMapping("/all")
    fun getAllCenters(): ResponseEntity<Any> {
        val centers = centerService.getAllCenters().map { center ->
            CenterDto(
                centerId = center.centerId,
                centerName = center.centerName,
                photos = StringConversionUtils.convertStringToList(center.photos ?: ""),
                locationProvince = center.locationProvince,
                locationCity = center.locationCity,
                description = center.description,
                userId = center.user.userId
            )
        }
        return ResponseEntity.ok().body(centers.takeIf { it.isNotEmpty() } ?: "등록된 센터가 존재하지 않습니다.")
    }

    @PostMapping("/add")
    fun addCenter(@RequestBody centerDto: CenterDto, request: HttpServletRequest): ResponseEntity<Any> {
        val user = userService.findUserById(centerDto.userId)
            ?: throw UserNotFoundException(centerDto.userId)

        val photoString = StringConversionUtils.convertListToString(centerDto.photos ?: emptyList())
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
            ?: throw UserNotFoundException(userId)

        val centers = centerService.getCenterByUserId(user.userId).map { center ->
            CenterDto(
                centerId = center.centerId,
                centerName = center.centerName,
                photos = StringConversionUtils.convertStringToList(center.photos ?: ""),
                locationProvince = center.locationProvince,
                locationCity = center.locationCity,
                description = center.description,
                userId = center.user.userId
            )
        }
        return ResponseEntity.ok().body(centers.takeIf { it.isNotEmpty() } ?: "등록된 센터가 존재하지 않습니다.")
    }

    @PutMapping("/update/{centerId}")
    fun updateCenter(@PathVariable centerId: Int,@RequestBody @Valid updateCenterDto: UpdateCenterDto): ResponseEntity<Any>{
        val center = centerService.findById(centerId)
            ?: throw CenterNotFoundException(centerId)
        if (updateCenterDto.userId != center.user.userId) {
            throw CenterOwnershipException(updateCenterDto.userId, centerId)
        }
        val updateCenter = centerService.updateCenter(centerId, updateCenterDto)
        return ResponseEntity.ok(updateCenter)
    }
    @DeleteMapping("/delete/{centerId}")
    fun deleteCenter(@PathVariable centerId: Int): ResponseEntity<Any>{
        val center = centerService.findById(centerId)
            ?: throw CenterNotFoundException(centerId)
        centerService.deleteCenter(centerId)
        return ResponseEntity.ok().body("센터가 성공적으로 삭제되었습니다.")
    }

    // 검색 기능 추가
    @GetMapping("/search")
    fun searchCenters(
        @RequestParam(required = false) centerName: String?,
        @RequestParam(required = false) locationProvince: String?,
        @RequestParam(required = false) locationCity: String?,
        @RequestParam(defaultValue = "10") pageable: Pageable
    ): ResponseEntity<Page<CenterDto>> {
        val page = centerService.searchCenters(centerName, locationProvince, locationCity, pageable)
        val pageDto = page.map {center ->
            CenterDto(
                centerId = center.centerId,
                centerName = center.centerName,
                photos = StringConversionUtils.convertStringToList(center.photos ?: ""),
                locationProvince = center.locationProvince,
                locationCity = center.locationCity,
                description = center.description,
                userId = center.user.userId
            )

        }
        return ResponseEntity.ok().body(pageDto)
    }
}
