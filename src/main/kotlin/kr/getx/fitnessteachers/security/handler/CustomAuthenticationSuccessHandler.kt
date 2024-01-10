package kr.getx.fitnessteachers.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.security.jwt.JwtTokenProvider
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
class CustomAuthenticationSuccessHandler(
    private val jwtTokenProvider: JwtTokenProvider
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val authorities = authentication.authorities.map { it.authority }
        val username = (authentication.principal as User).username
        // 만약 user.roles를 사용하게 된다면, user 엔티티에 roles 필드를 추가해야 함
        //val token = jwtTokenProvider.createToken(user.username, user.roles)
        val token = jwtTokenProvider.createToken(username, authorities)

        // JWT 토큰을 응답 헤더에 추가
        response.addHeader("Authorization", "Bearer $token")

        // 리다이렉트 URL 설정
        val redirectUrl = "/api/users/all" // 리다이렉트할 URL 지정

        // 사용자를 리다이렉트 URL로 이동
        response.sendRedirect(redirectUrl)
    }
}
