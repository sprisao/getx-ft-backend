package kr.getx.fitnessteachers.service

import jakarta.persistence.EntityNotFoundException
import kr.getx.fitnessteachers.dto.UserDto
import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.entity.User
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.nio.file.AccessDeniedException

@Service
class AuthenticationValidationService (
    private val userService: UserService,
    private val centerService: CenterService
) {
    // Auth, JWT를 통해 얻은 인증 정보를 통해 사용자 정보를 가져온후 대조
    fun getUserFromAuthentication(authentication: Authentication): User {
        val userDto = authentication.principal as UserDto
        val userEmail = userDto.email
            ?: throw IllegalArgumentException("인증 정보에서 사용자 이메일을 찾을 수 없습니다.")
        return userService.findUserByEmail(userEmail)
            ?: throw EntityNotFoundException("이메일이 $userEmail 인 사용자를 찾을 수 없습니다.")
    }

    // centerId 를 통해서 센터의 소유주인지 확인
    fun validateCenterOwnership(centerId: Int, user: User): Center {
        val center = centerService.findById(centerId)
            ?: throw EntityNotFoundException("ID가 $centerId 인 센터를 찾을 수 없습니다.")

        if (center.user.userId != user.userId) {
            throw AccessDeniedException("사용자 ${user.userId}는 센터 ${centerId}의 소유주가 아닙니다.")
        }

        return center
    }
}