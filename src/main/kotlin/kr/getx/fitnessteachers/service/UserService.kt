package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {
    private val logger = LoggerFactory.getLogger(UserService::class.java)

    fun findAll(): List<User> {
        val users = userRepository.findAll()
        logger.info("Fetched ${users.size} users from the database.")
        return users
    }
}