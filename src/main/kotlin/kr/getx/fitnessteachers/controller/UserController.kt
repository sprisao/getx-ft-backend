package kr.getx.fitnessteachers.controller

import jakarta.servlet.http.HttpServletRequest
import kr.getx.fitnessteachers.common.response.CommonResult
import kr.getx.fitnessteachers.common.service.ResponseService
import kr.getx.fitnessteachers.dto.UserDto
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.service.UserService
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
class UserController(
  private val userService: UserService,
) {

  @Autowired
  private lateinit var responseService: ResponseService

  @GetMapping("/all")
  fun getAllUsers(): List<User> = userService.getAllUsers()

  @PostMapping("/login")
  fun loginUser(request: HttpServletRequest): ResponseEntity<Any> {
      val userDto = request.getAttribute("userData") as? UserDto
      return try {
          val user = userService.processUserLogin(userDto!!)
          ResponseEntity.ok(user)
      } catch (e: Exception) {
          ResponseEntity.badRequest().body("Login or Registration Failed : ${e.message}")
      }
  }

    @PostMapping("/userTypeEdit")
    fun userTypeEdit(@RequestBody updateUserRequest: UserDto): ResponseEntity<Any> {
        return try {
            val updateUser = userService.processUserTypeEdit(updateUserRequest)
            ResponseEntity.ok().body(updateUser)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("User Type Edit Failed : ${e.message}")
        }
    }

    @PostMapping("/edit/{email}")
    fun editUser(@PathVariable email: String, @RequestBody updateUserRequest: UserDto): ResponseEntity<Any> {
        return try {
            val updateUser = userService.processUserEdit(email, updateUserRequest)
            ResponseEntity.ok().body(updateUser)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("User Edit Failed : ${e.message}")
        }
    }
    @GetMapping("/{email}")
    fun getUser(@PathVariable email: String): CommonResult = responseService.getSingleResult(userService.getUser(email))

    @DeleteMapping("/delete/{email}")
    fun deleteUser(@PathVariable email: String) = userService.deleteUser(email)
}