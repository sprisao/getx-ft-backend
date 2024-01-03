package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.entity.Resume
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.service.ResumeService
import kr.getx.fitnessteachers.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/resumes")
class ResumeController(private val resumeController: ResumeController) {

    @GetMapping
    fun getAllResumes(): List<Resume> = ResumeService.getAllResumes()

    @PostMapping("/add")
    fun addUser(@RequestBody user: User): User = userService.addUser(user)

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): User? = userService.getUserById(id)

    @PutMapping("/update")
    fun updateUser(@RequestBody user: User): User = userService.updateUser(user)

    @DeleteMapping("/delete/{id}")
    fun deleteUser(@PathVariable id: Long) = userService.deleteUser(id)
}
