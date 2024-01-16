package kr.getx.fitnessteachers.service

import jakarta.servlet.http.HttpServletResponse
import kr.getx.fitnessteachers.dto.UserData
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(id : Int): User? = userRepository.findById(id).orElse(null)

    fun deleteUser(id: Int) = userRepository.deleteById(id)

   fun processUserLogin(userData : UserData): User {
       val existingUser = userRepository.findByEmail(userData.email)
       return if (existingUser != null) {
           if (existingUser.email == userData.email && existingUser.socialType == userData.socialType) {
               loginUser(existingUser)
           } else {
                throw Exception("이미 가입된 이메일 입니다.")
           }
       } else {
           registerUser(userData)
       }
    }

    private fun loginUser(existingUser : User): User {
        return existingUser
    }

    private fun registerUser(userData : UserData): User {
        val newUser = User(
            name = userData.name,
            email = userData.email,
            socialType = userData.socialType,
        )
        return userRepository.save(newUser)
    }
}

