package kr.getx.fitnessteachers.service

import jakarta.persistence.EntityNotFoundException
import kr.getx.fitnessteachers.dto.UserDto
import kr.getx.fitnessteachers.entity.TeacherType
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.exceptions.UserLoginFailedException
import kr.getx.fitnessteachers.exceptions.UserNotFoundExceptionByEmail
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUser(email: String): User = userRepository.findByEmail(email) ?: throw UserNotFoundExceptionByEmail(email)

    fun findUserById(userId: Int): User =
        userRepository.findById(userId).orElseThrow { EntityNotFoundException("해당 유저 ID 값이 존재하지 않습니다. : $userId") }

    fun deleteUser(email: String) {
        val user =
            userRepository.findByEmailAndIsDeletedFalse(email).orElseThrow() { UserNotFoundExceptionByEmail(email) }
        user.isDeleted = true
        user.isDeletedAt = LocalDateTime.now()
        userRepository.save(user)
    }

    // 회원가입 & 로그인
    fun processUserLogin(userDto: UserDto): Map<String, Any> {
        val existingUser = userRepository.findByEmail(userDto.email) ?: return mapOf(
            "loginStatus" to false,
            "data" to registerUser(userDto)
        )
        if (existingUser.socialType != userDto.socialType) {
            throw UserLoginFailedException("이메일 로그인 실패 : 이미 다른 소셜로 가입된 이메일입니다.")
        }
        return mapOf("loginStatus" to true, "data" to loginUser(existingUser))
    }

    // 개인정보 추가 기입
    fun processUserTypeEdit(userDto: UserDto): User {
        val user = userRepository.findByEmail(userDto.email) ?: throw UserNotFoundExceptionByEmail(userDto.email)
        return updateUserDetails(user, userDto)
    }

    // 유저 정보 수정
    fun processUserEdit(email: String, userDto: UserDto): User {
        val user = userRepository.findByEmail(email) ?: throw UserNotFoundExceptionByEmail(email)
        return updateUserDetails(user, userDto)
    }

    private fun loginUser(user: User): User = user

    private fun registerUser(userDto: UserDto): User {
        val nickname = if (userDto.nickname.isBlank()) {
            "회원_&{UUID.randomUUID().toString.take(8)}"
        } else {
            userDto.nickname
        }

        return User(
            name = userDto.name,
            email = userDto.email,
            socialType = userDto.socialType,
            nickname = nickname,
            photo = userDto.photo,
            userType = userDto.userType,
            teacherType = userDto.teacherType ?: TeacherType.FITNESS,
            photoIsDisplay = userDto.photoIsDisplay,
            resumeExists = userDto.resumeExists,
            centerExists = userDto.centerExists,
            userTypeStatus = userDto.userTypeStatus,
            isDeleted = false,
        ).also {
            userRepository.save(it)
        }
    }

    private fun updateUserDetails(user: User, userDto: UserDto): User {
        user.apply {
            nickname = userDto.nickname
            photo = userDto.photo
            photoIsDisplay = userDto.photoIsDisplay
            userType = userDto.userType
            resumeExists = userDto.resumeExists
            centerExists = userDto.centerExists
            userTypeStatus = userDto.userTypeStatus
            teacherType = userDto.teacherType
        }
        return userRepository.save(user)
    }
}

