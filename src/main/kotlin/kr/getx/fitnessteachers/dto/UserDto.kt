package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.TeacherType
import kr.getx.fitnessteachers.entity.User
import java.time.LocalDateTime

data class UserDto(
    val userId: Int,
    val name: String,
    var nickname: String,
    val email: String,
    val socialType: String,
    var photoIsDisplay: Boolean = false,
    var photo: String? = "",
    var userType: Boolean? = false,
    var userTypeStatus: Boolean = false,
    var resumeExists: Boolean = false,
    var centerExists: Boolean = false,
    var isDeleted: Boolean,
    var isDeletedAt: LocalDateTime? = null,
) {
    companion object {
        fun fromEntity(user: User): UserDto {
            return UserDto(
                userId = user.userId,
                name = user.name,
                nickname = user.nickname,
                email = user.email,
                socialType = user.socialType,
                photo = user.photo,
                photoIsDisplay = user.photoIsDisplay,
                userType = user.userType,
                resumeExists = user.resumeExists,
                centerExists = user.centerExists,
                userTypeStatus = user.userTypeStatus,
                isDeleted = user.isDeleted,
                isDeletedAt = user.isDeletedAt
            )
        }
    }
}
