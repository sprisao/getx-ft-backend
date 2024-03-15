package kr.getx.fitnessteachers.dto

data class UserDto(
    val name: String,
    var nickname: String? = "",
    val email: String,
    val socialType: String,
    var profileStatus: Boolean = false,
    var profileUrl: String? = "",
    var userType: String? = "",
    var userTypeStatus: Boolean = false,
    var resumeStatus: Boolean = false,
    var centerStatus: Boolean = false
)
