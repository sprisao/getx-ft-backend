package kr.getx.fitnessteachers.dto

data class UserData(
    val name: String? = null,
    val email: String? = null,
    val socialType: String? = null,
    val profileStatus: Boolean? = null,
    val profileUrl: String? = null,
    val userType: String? = null,
    val userTypeStatus: Boolean? = null,
    val resumeStatus: Boolean? = null,
    val centerStatus: Boolean? = null
)
