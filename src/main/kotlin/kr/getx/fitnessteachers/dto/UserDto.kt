package kr.getx.fitnessteachers.dto

data class UserDto(
    val name: String,
    val email: String,
    val socialType: String? = null,
    val profileStatus: Boolean? = null,
    val profileUrl: String? = null,
    val userType: String? = null,
    val userTypeStatus: Boolean? = null,
    val resumeStatus: Boolean? = null,
    val centerStatus: Boolean? = null
)
