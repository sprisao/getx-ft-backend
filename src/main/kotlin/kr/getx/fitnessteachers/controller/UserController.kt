package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAllUsers(): List<User> = userService.getAllUsers()

    @PostMapping("/add")
    fun addUser(@RequestBody user: User): User = userService.addUser(user)

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): User? = userService.getUserById(id)

    @PutMapping("/update")
    fun updateUser(@RequestBody user: User): User = userService.updateUser(user)

    @DeleteMapping("/delete/{id}")
    fun deleteUser(@PathVariable id: Int) = userService.deleteUser(id)
}