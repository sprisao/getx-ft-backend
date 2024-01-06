package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(id : Int): User? = userRepository.findById(id).orElse(null)

    fun addOrUpdateUser(user: User): User {
        val existingUser = userRepository.findByUserSocialMediaId(user.userSocialMediaId)
        return existingUser.map {
            it.apply {
                email = user.email
                username = user.username
                phoneNumber = user.phoneNumber
                // 기타 필드 업데이트
            }
            userRepository.save(it)
        }.orElseGet { userRepository.save(user) }
    }

    fun getUserBySocialMediaId(userSocialMediaId: String): Optional<User> {
        return userRepository.findByUserSocialMediaId(userSocialMediaId)
    }

    fun deleteUser(id: Int) = userRepository.deleteById(id)
}

