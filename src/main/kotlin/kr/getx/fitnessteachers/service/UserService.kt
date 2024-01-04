package kr.getx.fitnessteachers.service

import jakarta.persistence.Id
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun addUser(user: User): User = userRepository.save(user)

    fun getUserById(id : Int): User? = userRepository.findById(id).orElse(null)

    fun updateUser(user: User): User = userRepository.save(user)

    fun deleteUser(id: Int) = userRepository.deleteById(id)
}

