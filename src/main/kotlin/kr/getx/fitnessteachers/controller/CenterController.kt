package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.dto.CenterDto
import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.exceptions.UserNotFoundException
import kr.getx.fitnessteachers.service.CenterService
import kr.getx.fitnessteachers.service.UserService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
@RestController
@RequestMapping("/api/centers")
class CenterController(
    private val centerService: CenterService,
    private val userService: UserService
) {

    @GetMapping("/all")
    fun getAllCenters(): ResponseEntity<List<CenterDto>> =
        ResponseEntity.ok(centerService.getAllCenters().map { CenterDto.fromEntity(it) })

    @PostMapping("/add")
    fun addCenter(@RequestBody centerDto: CenterDto): ResponseEntity<Center> =
        ResponseEntity.ok(centerService.addCenter(centerDto.toEntity(userService.findUserById(centerDto.userId)
            ?: throw UserNotFoundException(centerDto.userId))))

    @GetMapping("/{userId}")
    fun getCenterByUserId(@PathVariable userId: Int): ResponseEntity<List<CenterDto>> =
        ResponseEntity.ok(centerService.getCenterByUserId(userId).map { CenterDto.fromEntity(it) })

    @PutMapping("/update/{centerId}")
    fun updateCenter(@PathVariable centerId: Int,@RequestBody centerDto: CenterDto): ResponseEntity<Center> =
        ResponseEntity.ok(centerService.updateCenter(centerId, centerDto))

    @DeleteMapping("/delete/{centerId}")
    fun deleteCenter(@PathVariable centerId: Int): ResponseEntity<String>{
        centerService.deleteCenter(centerId)
        return ResponseEntity.ok().body("센터가 성공적으로 삭제되었습니다.")
    }
}
