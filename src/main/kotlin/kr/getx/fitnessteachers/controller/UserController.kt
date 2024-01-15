package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.common.response.CommonResult
import kr.getx.fitnessteachers.common.service.ResponseService
import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.repository.UserRepository
import kr.getx.fitnessteachers.service.JwtService
import kr.getx.fitnessteachers.service.ResumeService
import kr.getx.fitnessteachers.service.UserService
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
class UserController(
  private val userService: UserService,
  private val resumeService: ResumeService,
  private val userRepository: UserRepository,
  private val jwtService: JwtService
) {

  @Autowired
  private lateinit var responseService: ResponseService

  @GetMapping("/all")
  fun getAllUsers(): List<User> = userService.getAllUsers()

  @PostMapping("/social-login")
  fun handleSocialLogin(@RequestBody socialLoginData : SocialLoginData): ResponseEntity<String> {
      // 중복 이메일 확인
      userRepository.findByEmail(socialLoginData.email)?.let {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입된 이메일입니다.")
      }

    // 프론트에서 받은 데이터 처리
    val user = User(
        username = socialLoginData.username,
        email = socialLoginData.email,
        phoneNumber = socialLoginData.phoneNumber,
        userSocialMediaId = socialLoginData.userSocialMediaId
    )

    // 유저 정보 저장
    userRepository.save(user)

    // JWT 토큰 생성
    val token = jwtService.createToken(user.userSocialMediaId, user.username)

    return ResponseEntity.ok(token)
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


data class SocialLoginData(
    val username: String,
    val email: String,
    val phoneNumber: String,
    val userSocialMediaId: String,
    val accessToken: String=""
)