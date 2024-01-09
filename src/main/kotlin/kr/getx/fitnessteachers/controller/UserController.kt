package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.common.response.CommonResult
import kr.getx.fitnessteachers.common.service.ResponseService
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.service.ResumeService
import kr.getx.fitnessteachers.service.UserService
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
class UserController(private val userService: UserService, private val resumeService: ResumeService) {

  @Autowired
  private lateinit var responseService: ResponseService


  @GetMapping("/all")
  fun getAllUsers(): List<User> = userService.getAllUsers()

  @PostMapping("/add")
  fun addUserFromNaver(authentication: OAuth2AuthenticationToken): ResponseEntity<User> {
    val naverUser = authentication.principal.attributes["response"] as Map<String, Any?>
    val user = User(
      userSocialMediaId = naverUser["id"] as String,
      email = naverUser["email"] as String,
      username = naverUser["name"] as String,
      phoneNumber = naverUser["mobile"] as String
    )
    val savedUser = userService.addOrUpdateUser(user)
    return ResponseEntity.ok(savedUser)
  }

  @GetMapping("/{id}")
  fun getUser(@PathVariable id: Int): CommonResult = responseService.getSingleResult(userService.getUserById(id))

  @GetMapping("/{userSocialMediaId}")
  fun getUserBySocialMediaId(@PathVariable userSocialMediaId: String): ResponseEntity<User> {
    val user = userService.getUserBySocialMediaId(userSocialMediaId)
    return user.map { ResponseEntity.ok(it) }
      .orElseGet { ResponseEntity.status(HttpStatus.NOT_FOUND).build() }
  }
  @DeleteMapping("/delete/{id}")
  fun deleteUser(@PathVariable id: Int) = userService.deleteUser(id)

  @GetMapping("/{id}/resume")
  fun getUserAndResume(@PathVariable id: Int): ResponseEntity<Pair<User?, Resume?>> {
    val user = userService.getUserById(id)
    val resume = resumeService.getResumeByUserId(id)
    return ResponseEntity.ok(Pair(user, resume))
  }
}
