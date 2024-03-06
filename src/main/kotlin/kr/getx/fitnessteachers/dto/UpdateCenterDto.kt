package kr.getx.fitnessteachers.dto

data class UpdateCenterDto(
    var userId: Int,
    var centerName: String?,
    var photos: List<String>,
    var locationProvince: String?,
    var locationCity: String?,
    var description: String?
)