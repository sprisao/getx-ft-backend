package kr.getx.fitnessteachers.service

import jakarta.persistence.EntityNotFoundException
import kr.getx.fitnessteachers.dto.UserDto
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.http.ResponseEntity
@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUser(email: String?): User? = userRepository.findByEmail(email)

    fun findUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun findUserById(userId: Int): User?{
        return userRepository.findById(userId).orElseThrow {
            EntityNotFoundException("User not found with ID: $userId")
        }
    }

    fun deleteUser(email: String) {
        val user = userRepository.findByEmail(email)
        if (user != null) {
            userRepository.delete(user)
        }
    }

    // 회원가입 & 로그인
   fun processUserLogin(userDto : UserDto): ResponseEntity<Map<String, Any>> {
       val existingUser = userRepository.findByEmail(userDto.email)
       return if (existingUser != null) {
           if (existingUser.socialType == userDto.socialType) {
               ResponseEntity.ok(mapOf("loginStatus" to true, "data" to loginUser(existingUser)))
           } else {
                ResponseEntity
                    .ok(mapOf("loginStatus" to false, "data" to "이미 다른 소셜로 가입된 이메일 입니다."))
           }
       } else {
           ResponseEntity.ok(mapOf("loginStatus" to true, "data" to registerUser(userDto)))
       }
   }

   // 개인정보 추가 기입 (유저 타입, 프로필 사진, 프로필 상태, 유저 타입 상태, 이력서 상태, 센터 상태, 유저 타입)
    fun processUserTypeEdit(userDto : UserDto): User {
        val editUser = userRepository.findByEmail(userDto.email) ?: throw RuntimeException("User Not Found")

        editUser.profileUrl = userDto.profileUrl
        editUser.userType = userDto.userType
        editUser.profileStatus = userDto.profileStatus
        editUser.userTypeStatus = userDto.userTypeStatus
        editUser.resumeStatus = userDto.resumeStatus
        editUser.centerStatus = userDto.centerStatus

        return userRepository.save(editUser)
    }

    private fun loginUser(existingUser : User): User {
        return existingUser
    }

    private fun registerUser(userDto : UserDto): User {
        val name = userDto.name ?: throw IllegalArgumentException("Name is missing in the token")
        val email = userDto.email ?: throw IllegalArgumentException("Email is missing in the token")
        val socialType = userDto.socialType ?: throw IllegalArgumentException("Social Type is missing in the token")
        val newUser = User(
            name = name,
            email = email,
            socialType = socialType
        )
        return userRepository.save(newUser)
    }

    fun processUserEdit(email: String, userDto: UserDto): User {
        val editUser = userRepository.findByEmail(email) ?: throw RuntimeException("User Not Found")

        editUser.profileUrl = userDto.profileUrl
        editUser.profileStatus = userDto.profileStatus
        editUser.resumeStatus = userDto.resumeStatus
        editUser.centerStatus = userDto.centerStatus

        return userRepository.save(editUser)
    }
}

