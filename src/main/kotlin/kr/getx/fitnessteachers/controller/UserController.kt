package kr.getx.fitnessteachers.controller

import jakarta.servlet.http.HttpServletRequest
import kr.getx.fitnessteachers.dto.UserDto
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.exceptions.AuthenticationEmailNotFoundException
import kr.getx.fitnessteachers.exceptions.UserLoginFailedException
import kr.getx.fitnessteachers.service.UserService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
class UserController(
  private val userService: UserService,
)   {

    @GetMapping("/all")
    fun getAllUsers(): ResponseEntity<List<User>> = ResponseEntity.ok(userService.getAllUsers())
    @GetMapping("/{email}")
    fun getUser(@PathVariable email: String): ResponseEntity<User> = ResponseEntity.ok(userService.getUser(email))
    @DeleteMapping("/delete/{email}")
    fun deleteUser(@PathVariable email: String): ResponseEntity<Void> {
        userService.deleteUser(email)
        return ResponseEntity.ok().build()
    }
    @PostMapping("/login")
    fun loginUser(request: HttpServletRequest): ResponseEntity<Any> {
      val userDto = request.getAttribute("userData") as? UserDto
          ?: throw AuthenticationEmailNotFoundException()

      return try {
          val user = userService.processUserLogin(userDto)
          ResponseEntity.ok(user)
      } catch (e: Exception) {
        throw UserLoginFailedException("로그인 및 회원가입에 실패했습니다. : ${e.message}")
      }
    }

    @PostMapping("/userTypeEdit")
    fun userTypeEdit(@RequestBody updateUserRequest: UserDto): ResponseEntity<User> = ResponseEntity.ok(userService.processUserTypeEdit(updateUserRequest))

    @PostMapping("/edit/{email}")
    fun editUser(@PathVariable email: String, @RequestBody updateUserRequest: UserDto): ResponseEntity<User> = ResponseEntity.ok(userService.processUserEdit(email, updateUserRequest))

}