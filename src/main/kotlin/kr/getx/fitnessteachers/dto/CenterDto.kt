package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.utils.StringConversionUtils


data class CenterDto(
        val centerId: Int? = null,
        var centerName: String? = null,
        var photos: List<String>? = null,
        var locationProvince: String? = null,
        var locationCity: String? = null,
        var description: String? = null,
        var userId: Int
) {
        companion object {
                fun fromEntity(center: Center): CenterDto {
                        return CenterDto(
                                centerId = center.centerId,
                                centerName = center.centerName,
                                photos = StringConversionUtils.convertStringToList(center.photos ?: ""),
                                locationProvince = center.locationProvince,
                                locationCity = center.locationCity,
                                description = center.description,
                                userId = center.user.userId
                        )
                }
        }
        fun toEntity(user: User): Center {
                return Center(
                        centerId = this.centerId ?: 0,
                        centerName = this.centerName ?: "",
                        photos = StringConversionUtils.convertListToString(this.photos ?: emptyList()),
                        locationProvince = this.locationProvince ?: "",
                        locationCity = this.locationCity ?: "",
                        description = this.description ?: "",
                        user = user
                )
        }
}



