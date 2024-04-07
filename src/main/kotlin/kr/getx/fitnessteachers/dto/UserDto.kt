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
    var profileStatus: Boolean = false,
    var profileUrl: String? = "",
    var userType: Boolean? = false,
    var teacherType: TeacherType? = null,
    var userTypeStatus: Boolean = false,
    var resumeStatus: Boolean = false,
    var centerStatus: Boolean = false
) {
    fun toEntity(): User {
        return User(
            userId = this.userId,
            name = this.name,
            nickname = this.nickname,
            email = this.email,
            socialType = this.socialType,
            profileUrl = this.profileUrl,
            userType = this.userType,
            teacherType = this.teacherType,
            profileStatus = this.profileStatus,
            resumeStatus = this.resumeStatus,
            centerStatus = this.centerStatus,
            userTypeStatus = this.userTypeStatus,
            createdAt = LocalDateTime.now() // 이 부분은 생성 시점에 따라 다르게 설정될 수 있습니다.
        )
    }

    companion object {
        fun fromEntity(user: User): UserDto {
            return UserDto(
                userId = user.userId,
                name = user.name,
                nickname = user.nickname,
                email = user.email,
                socialType = user.socialType,
                profileUrl = user.profileUrl,
                userType = user.userType,
                teacherType = user.teacherType,
                profileStatus = user.profileStatus,
                resumeStatus = user.resumeStatus,
                centerStatus = user.centerStatus,
                userTypeStatus = user.userTypeStatus
            )
        }
    }
}
