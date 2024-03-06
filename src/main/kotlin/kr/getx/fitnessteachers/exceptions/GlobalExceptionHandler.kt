package kr.getx.fitnessteachers.exceptions

import kr.getx.fitnessteachers.exceptions.*
import kr.getx.fitnessteachers.service.AuthenticationValidationService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

    // 사용자 로그인 예외 처리
    @ExceptionHandler(UserLoginFailedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUserLoginFailed(ex: UserLoginFailedException): String = ex.message ?: "User login failed"

    // 사용자 편집 예외 처리
    @ExceptionHandler(UserEditFailedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUserEditFailed(ex: UserEditFailedException): String = ex.message ?: "User edit failed"

    // 사용자 예외 처리
    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserNotFound(ex: UserNotFoundException): String = ex.message ?: "User not found"

    // 사용자 이메일 예외 처리
    @ExceptionHandler(UserNotFoundExceptionByEmail::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserNotFoundByEmail(ex: UserNotFoundExceptionByEmail): String = ex.message ?: "User not found by email"

    // 사용자 이메일 인증 예외 처리
    @ExceptionHandler(AuthenticationEmailNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleAuthenticationEmailNotFound(ex: AuthenticationEmailNotFoundException): String = ex.message ?: "Authentication email not found"

    // 이력서 예외 처리
    @ExceptionHandler(ResumeNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleResumeNotFound(ex: ResumeNotFoundException): String = ex.message ?: "Resume not found"

    // 센터 예외 처리
    @ExceptionHandler(CenterNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleCenterNotFound(ex: CenterNotFoundException): String = ex.message ?: "Center not found"

    // 센터 소유권 예외 처리
    @ExceptionHandler(CenterOwnershipException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleCenterOwnership(ex: CenterOwnershipException): String = ex.message ?: "Center ownership violation"

    // 이력서 작업 예외 처리
    @ExceptionHandler(InvalidResumeOperationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidResumeOperation(ex: InvalidResumeOperationException): String = ex.message ?: "Invalid resume operation"

    // 구인 게시물 예외 처리
    @ExceptionHandler(JobPostNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleJobPostNotFound(ex: JobPostNotFoundException): String = ex.message ?: "JobPost not found"
}