package kr.getx.fitnessteachers.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.getx.fitnessteachers.dto.UserDto
import kr.getx.fitnessteachers.entity.User
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

class JwtTokenFilter(private val jwtUtils: JwtUtils) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = jwtUtils.resolveToken(request)
        if (token != null && jwtUtils.validateToken(token)) {
            // Jwt 토큰에서 소셜 로그인 정보 추출
            val userData = jwtUtils.getSocialLoginInfo(token)
            if(userData != null) {
                val name = userData.name ?: throw IllegalArgumentException("Name is missing in the token")
                val email = userData.email ?: throw IllegalArgumentException("Email is missing in the token")
                val socialType = userData.socialType ?: throw IllegalArgumentException("Social Type is missing in the token")

                val userDto = UserDto(
                    name = name,
                    email = email,
                    socialType = socialType
                )
                // 인증 정보 저장
                val auth = UsernamePasswordAuthenticationToken(
                    userDto,
                    null,
                    listOf(SimpleGrantedAuthority("ROLE_USER")) // 권한 설정
                )
                SecurityContextHolder.getContext().authentication = auth
                request.setAttribute("userData", userData)
            }
            filterChain.doFilter(request, response)
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 값이 유효하지 않습니다.")
        }
    }
}