package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun addUser(user: User): User = userRepository.save(user)

    fun getUserById(id: Long): User? = userRepository.findById(id).orElse(null)

    fun updateUser(user: User): User = userRepository.save(user)

    fun deleteUser(id: Long) = userRepository.deleteById(id)
}

