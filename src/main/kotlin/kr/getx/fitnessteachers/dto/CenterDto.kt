package kr.getx.fitnessteachers.dto


data class CenterDto(
        val centerId: Int,
        var centerName: String? = null,
        var photos: List<String>,
        var locationProvince: String? = null,
        var locationCity: String? = null,
        var description: String? = null,
        var userId: Int
)