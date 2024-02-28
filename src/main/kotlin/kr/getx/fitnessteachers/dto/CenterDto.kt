package kr.getx.fitnessteachers.dto

data class CenterDto(
        val centerName: String? = null,
        val photos: List<String>,
        val locationProvince: String? = null,
        val locationCity: String? = null,
        val description: String? = null,
        val userId: Int  // 사용자 ID 추가
)