package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.common.response.CommonResult
import kr.getx.fitnessteachers.common.response.SingleResult
import kr.getx.fitnessteachers.common.service.ResponseService
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.service.UserService
import lombok.RequiredArgsConstructor
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

  @Autowired
  private lateinit var responseService: ResponseService

  @GetMapping("/all")
  fun getAllUsers(): List<User> = userService.getAllUsers()

  @PostMapping("/add")
  fun addUser(@RequestBody user: User): User = userService.addUser(user)

  @GetMapping("/{id}")
  fun getUser(@PathVariable id: Int): CommonResult = responseService.getSingleResult(userService.getUserById(id))

  @PutMapping("/update")
  fun updateUser(@RequestBody user: User): User = userService.updateUser(user)

  @DeleteMapping("/delete/{id}")
  fun deleteUser(@PathVariable id: Int) = userService.deleteUser(id)
}
