package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.common.response.CommonResult
import kr.getx.fitnessteachers.common.service.ResponseService
import kr.getx.fitnessteachers.dto.UserData
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.repository.UserRepository
import kr.getx.fitnessteachers.service.ResumeService
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
  private val resumeService: ResumeService,
) {

  @Autowired
  private lateinit var responseService: ResponseService

  @GetMapping("/all")
  fun getAllUsers(): List<User> = userService.getAllUsers()

  @PostMapping("/login")
  fun loginUser(@RequestBody userData: UserData): ResponseEntity<Any> {
      return try {
          val user = userService.processUserLogin(userData)
          ResponseEntity.ok(user)
      } catch (e: Exception) {
          ResponseEntity.badRequest().body("Login or Registration Failed")
      }
  }

  @GetMapping("/{id}")
  fun getUser(@PathVariable id: Int): CommonResult = responseService.getSingleResult(userService.getUserById(id))

  @DeleteMapping("/delete/{id}")
  fun deleteUser(@PathVariable id: Int) = userService.deleteUser(id)

  @GetMapping("/{id}/resume")
  fun getUserAndResume(@PathVariable id: Int): ResponseEntity<Pair<User?, Resume?>> {
    val user = userService.getUserById(id)
    val resume = resumeService.getResumeByUserId(id)
    return ResponseEntity.ok(Pair(user, resume))
  }
}