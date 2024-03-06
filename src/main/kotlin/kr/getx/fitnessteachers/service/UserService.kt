package kr.getx.fitnessteachers.service

import jakarta.persistence.EntityNotFoundException
import kr.getx.fitnessteachers.dto.UserDto
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.exceptions.UserLoginFailedException
import kr.getx.fitnessteachers.exceptions.UserNotFoundException
import kr.getx.fitnessteachers.exceptions.UserNotFoundExceptionByEmail
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
            ?: throw UserNotFoundExceptionByEmail(email)
        userRepository.delete(user)
    }

    // 회원가입 & 로그인
   fun processUserLogin(userDto : UserDto): ResponseEntity<Map<String, Any>> {
       val existingUser = userRepository.findByEmail(userDto.email) ?: return ResponseEntity.ok(mapOf("loginStatus" to true, "data" to registerUser(userDto)))
        if (existingUser.socialType != userDto.socialType) {
            throw UserLoginFailedException("이미 다른 소셜로 가입된 이메일입니다: ${userDto.email}")
        }

        return ResponseEntity.ok(mapOf("loginStatus" to true, "data" to registerUser(userDto)))
   }

   // 개인정보 추가 기입 (유저 타입, 프로필 사진, 프로필 상태, 유저 타입 상태, 이력서 상태, 센터 상태, 유저 타입)
    fun processUserTypeEdit(userDto : UserDto): User {
        val editUser = userRepository.findByEmail(userDto.email) ?: throw UserNotFoundExceptionByEmail(userDto.email)

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
        return User (
            name = userDto.name ?: throw IllegalArgumentException("Name is missing in the token"),
            email = userDto.email ?: throw IllegalArgumentException("Email is missing in the token"),
            socialType = userDto.socialType ?: throw IllegalArgumentException("Social Type is missing in the token"),
        ).also { userRepository.save(it) }
    }

    fun processUserEdit(email: String, userDto: UserDto): User {
        val editUser = userRepository.findByEmail(email) ?: throw UserNotFoundExceptionByEmail(email)

        editUser.apply {
            profileUrl = userDto.profileUrl
            profileStatus = userDto.profileStatus
            resumeStatus = userDto.resumeStatus
            centerStatus = userDto.centerStatus
        }

        return userRepository.save(editUser)
    }
}

