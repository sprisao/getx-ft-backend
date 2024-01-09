package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Int>{
    fun findByUserSocialMediaId(userSocialMediaId: String): Optional<User>

}