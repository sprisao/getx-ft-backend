package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.utils.StringConversionUtils


data class CenterDto(
        val centerId: Int,
        var centerName: String? = null,
        var photos: List<String>? = null,
        var roadAddress: String? = null,
        var sido: String? = null,
        var sidoEnglish: String? = null,
        var sigungu: String? = null,
        var sigunguEnglish: String? = null,
        var description: String? = null,
        var isDeleted: Boolean = false,
        var userId: Int
) {
        companion object {
                fun fromEntity(center: Center): CenterDto {
                        return CenterDto(
                                centerId = center.centerId,
                                centerName = center.centerName,
                                photos = StringConversionUtils.convertStringToList(center.photos ?: ""),
                                roadAddress = center.roadAddress,
                                sido = center.sido,
                                sidoEnglish = center.sidoEnglish,
                                sigungu = center.sigungu,
                                sigunguEnglish = center.sigunguEnglish,
                                description = center.description,
                                isDeleted = center.isDeleted,
                                userId = center.user.userId
                        )
                }
        }
        fun toEntity(user: User): Center {
                return Center(
                        centerId = this.centerId,
                        centerName = this.centerName ?: "",
                        photos = StringConversionUtils.convertListToString(this.photos ?: emptyList()),
                        roadAddress = this.roadAddress ?: "",
                        sido = this.sido ?: "",
                        sidoEnglish = this.sidoEnglish ?: "",
                        sigungu = this.sigungu ?: "",
                        sigunguEnglish = this.sigunguEnglish ?: "",
                        description = this.description ?: "",
                        isDeleted = this.isDeleted,
                        user = user
                )
        }
}



