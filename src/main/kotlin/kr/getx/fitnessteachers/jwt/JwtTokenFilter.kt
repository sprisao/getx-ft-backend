package kr.getx.fitnessteachers.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.getx.fitnessteachers.dto.UserDto
import kr.getx.fitnessteachers.entity.TeacherType
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

class JwtTokenFilter(private val jwtUtils: JwtUtils) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        if(request.method.equals("OPTIONS", ignoreCase = true)) {
            filterChain.doFilter(request, response)
            return
        }

        val token = jwtUtils.resolveToken(request)
        if (token != null && jwtUtils.validateToken(token)) {
            val userData = jwtUtils.getSocialLoginInfo(token)
            val userDto = UserDto(userId = 0, name = userData.name, email = userData.email, socialType = userData.socialType, teacherType = userData.teacherType, nickname = userData.nickname, photoIsDisplay = userData.photoIsDisplay, photo = userData.photo, userType = userData.userType, userTypeStatus = userData.userTypeStatus, resumeExists = userData.resumeExists, centerExists = userData.centerExists)
            val auth = UsernamePasswordAuthenticationToken(userDto, null, listOf(SimpleGrantedAuthority("ROLE_USER")))
            SecurityContextHolder.getContext().authentication = auth
            request.setAttribute("userData", userData)
        }

        filterChain.doFilter(request, response)
    }
}
    /*

    private val publicEndpoint = arrayOf(
        "/**",
    )

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val requestURI = request.requestURI
        if(publicEndpoint.any { requestURI.startsWith(it) }) {
            filterChain.doFilter(request, response)
            return
        }

        if(request.method.equals("OPTIONS", ignoreCase = true)) {
            filterChain.doFilter(request, response)
            return
        }

        val token = jwtUtils.resolveToken(request)
        if (token != null && jwtUtils.validateToken(token)) {
            // Jwt 토큰에서 소셜 로그인 정보 추출
            val userData = jwtUtils.getSocialLoginInfo(token)
            val userDto = UserDto(
                name = userData.name,
                email = userData.email,
                socialType = userData.socialType
            )
            // 인증 정보 저장
            val auth = UsernamePasswordAuthenticationToken(
                userDto,
                null,
                listOf(SimpleGrantedAuthority("ROLE_USER")) // 권한 설정
            )
            SecurityContextHolder.getContext().authentication = auth
            request.setAttribute("userData", userData)

            filterChain.doFilter(request, response)
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 값이 유효하지 않습니다.")
        }
    }
     */