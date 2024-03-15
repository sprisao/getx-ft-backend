package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.UserDto
import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.entity.User
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import kr.getx.fitnessteachers.exceptions.AuthenticationEmailNotFoundException
import kr.getx.fitnessteachers.exceptions.CenterNotFoundException
import kr.getx.fitnessteachers.exceptions.CenterOwnershipException
import kr.getx.fitnessteachers.exceptions.UserNotFoundExceptionByEmail

@Service
class AuthenticationValidationService (
    private val userService: UserService,
    private val centerService: CenterService
) {
    // Auth, JWT를 통해 얻은 인증 정보를 통해 사용자 정보를 가져온후 대조
    fun getUserFromAuthentication(authentication: Authentication): User {
        val userDto = authentication.principal as UserDto
        val userEmail = userDto.email
            ?: throw AuthenticationEmailNotFoundException()
        return userService.findUserByEmail(userEmail)
            ?: throw UserNotFoundExceptionByEmail(userEmail)
    }

    // centerId 를 통해서 센터의 소유주인지 확인
    fun validateCenterOwnership(centerId: Int, user: User): Center {
        val center = centerService.findById(centerId)
            ?: throw CenterNotFoundException(centerId)

        if (center.user.userId != user.userId) {
            throw CenterOwnershipException(centerId, user.userId)
        }

        return center
    }

    fun getUserById(userId: Int): User {
        return userService.findUserById(userId)
            ?: throw UserNotFoundExceptionByEmail("해당값의 유저가 존재하지 않습니다. : $userId")
    }
}