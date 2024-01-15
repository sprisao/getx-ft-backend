package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(id : Int): User? = userRepository.findById(id).orElse(null)

    fun addOrUpdateUser(userSocialMediaId: String, userInfo: Map<String, Any>): User {
        val existingUser = userRepository.findByUserSocialMediaId(userSocialMediaId)
        val user = existingUser?.apply {
            this.username = userInfo["name"] as String
            this.email = userInfo["email"] as String
            this.phoneNumber = userInfo["mobile"] as String
        } ?: User(
            userSocialMediaId = userSocialMediaId,
            username = userInfo["name"] as String,
            email = userInfo["email"] as String,
            phoneNumber = userInfo["mobile"] as String
        )
        return userRepository.save(user)
    }

    fun deleteUser(id: Int) = userRepository.deleteById(id)
}

