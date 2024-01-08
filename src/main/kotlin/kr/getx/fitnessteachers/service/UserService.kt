package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(id : Int): User? = userRepository.findById(id).orElse(null)

    fun deleteUser(id: Int) = userRepository.deleteById(id)
}

