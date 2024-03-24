package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.UserDto
import kr.getx.fitnessteachers.entity.Center
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.exceptions.*
import kr.getx.fitnessteachers.repository.JobPostRepository
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class AuthenticationValidationService (
    private val userService: UserService,
    private val centerService: CenterService,
    private val jobPostRepository: JobPostRepository
) {
    // Auth, JWT를 통해 얻은 인증 정보를 통해 사용자 정보를 가져온후 대조
    fun getUserFromAuthentication(authentication: Authentication): User {
        val userDto = authentication.principal as UserDto
        val userEmail = userDto.email
            ?: throw AuthenticationEmailNotFoundException()
        return userService.getUser(userEmail)
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

    // 요청한 사용자가 자신의 정보만 조회할 수 있도록 검증
    fun validateRequestingUser(userId: Int, authentication: Authentication) {
        val user = getUserFromAuthentication(authentication)
        if (user.userId != userId) {
            throw CenterOwnershipException(userId, user.userId)
        }
    }

    // 구인게시판 수정시 센터 소유주인지 확인
    fun validateJobPostModification(jobPostId: Int, user: User) {
        val jobPost = jobPostRepository.findById(jobPostId).orElseThrow { JobPostNotFoundException(jobPostId) }
        validateCenterOwnership(jobPost.center.centerId, user)
    }

    fun validateJobPostDeletion(jobPostId: Int, user: User) {
        val jobPost = jobPostRepository.findById(jobPostId).orElseThrow { JobPostNotFoundException(jobPostId) }
        validateCenterOwnership(jobPost.center.centerId, user)
    }
}