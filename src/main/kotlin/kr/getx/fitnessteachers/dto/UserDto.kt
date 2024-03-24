package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.TeacherType

data class UserDto(
    val name: String,
    var nickname: String? = "",
    val email: String,
    val socialType: String,
    var profileStatus: Boolean = false,
    var profileUrl: String? = "",
    var userType: Boolean? = false,
    var teacherType: TeacherType? = null,
    var userTypeStatus: Boolean = false,
    var resumeStatus: Boolean = false,
    var centerStatus: Boolean = false
)
