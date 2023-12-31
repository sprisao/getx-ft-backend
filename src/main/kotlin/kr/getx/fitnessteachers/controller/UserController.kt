package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.service.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userService: UserService) {

    @RequestMapping("/users")
    fun getUsers(): List<User> {
        return userService.findAll()
    }

}