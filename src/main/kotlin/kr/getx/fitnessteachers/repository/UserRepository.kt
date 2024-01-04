package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int>{
}