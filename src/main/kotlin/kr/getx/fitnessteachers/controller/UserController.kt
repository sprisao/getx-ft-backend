package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.common.response.CommonResult
import kr.getx.fitnessteachers.common.service.ResponseService
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.service.UserService
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.http.ResponseEntity

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

  @Autowired
  private lateinit var responseService: ResponseService

  @GetMapping("/all")
  fun getAllUsers(): List<User> = userService.getAllUsers()

  @PostMapping("/add")
  fun addUserFromNaver(authentication: OAuth2AuthenticationToken): ResponseEntity<User> {
    val naverUser = authentication.principal.attributes
    val user = User(
      User_Social_Media_ID = naverUser["id"] as String,
      Email = naverUser["email"] as String,
      Username = naverUser["name"] as String,
      PhoneNumber = naverUser["mobile"] as String
    )
    val savedUser = userService.addUser(user)
    return ResponseEntity.ok(savedUser)
  }

  @GetMapping("/{id}")
  fun getUser(@PathVariable id: Int): CommonResult = responseService.getSingleResult(userService.getUserById(id))

  @PutMapping("/update")
  fun updateUser(@RequestBody user: User): User = userService.updateUser(user)

  @DeleteMapping("/delete/{id}")
  fun deleteUser(@PathVariable id: Int) = userService.deleteUser(id)
}
