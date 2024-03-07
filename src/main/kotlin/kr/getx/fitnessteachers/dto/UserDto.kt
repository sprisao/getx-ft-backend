package kr.getx.fitnessteachers.dto

data class UserDto(
    val name: String,
    val email: String,
    val socialType: String? = null,
    var profileStatus: Boolean? = null,
    var profileUrl: String? = null,
    var userType: String? = null,
    var userTypeStatus: Boolean? = null,
    var resumeStatus: Boolean? = null,
    var centerStatus: Boolean? = null
)
