package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.UserData
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserByEmail(email: String): User?{
        return userRepository.findByEmail(email)
    }

    fun deleteUser(email: String) {
        val user = userRepository.findByEmail(email)
        if (user != null) {
            userRepository.delete(user)
        }
    }

    // 회원가입 & 로그인
   fun processUserLogin(userData : UserData): User {
       val existingUser = userRepository.findByEmail(userData.email)
       return if (existingUser != null) {
           if (existingUser.socialType == userData.socialType) {
               loginUser(existingUser)
           } else {
                throw Exception("이미 다른 소셜로 가입된 이메일 입니다.")
           }
       } else {
           registerUser(userData)
       }
   }

   // 개인정보 추가 기입 (유저 타입, 프로필 사진, 프로필 상태, 유저 타입 상태, 이력서 상태, 센터 상태, 유저 타입)
    fun processUserTypeEdit(userData : UserData): User {
        val editUser = userRepository.findByEmail(userData.email) ?: throw RuntimeException("User Not Found")

        editUser.profileUrl = userData.profileUrl
        editUser.userType = userData.userType
        editUser.profileStatus = userData.profileStatus
        editUser.userTypeStatus = userData.userTypeStatus
        editUser.resumeStatus = userData.resumeStatus
        editUser.centerStatus = userData.centerStatus

        return userRepository.save(editUser)
    }

    private fun loginUser(existingUser : User): User {
        return existingUser
    }

    private fun registerUser(userData : UserData): User {
        val name = userData.name ?: throw IllegalArgumentException("Name is missing in the token")
        val email = userData.email ?: throw IllegalArgumentException("Email is missing in the token")
        val socialType = userData.socialType ?: throw IllegalArgumentException("Social Type is missing in the token")
        val newUser = User(
            name = name,
            email = email,
            socialType = socialType
        )
        return userRepository.save(newUser)
    }
}

