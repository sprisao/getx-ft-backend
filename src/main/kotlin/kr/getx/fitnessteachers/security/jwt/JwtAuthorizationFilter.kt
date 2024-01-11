package kr.getx.fitnessteachers.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthorizationFilter(private val tokenProvider: TokenProvider) : OncePerRequestFilter() {

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = resolveToken(request)

        token?.let {
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                val authentication = tokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val token = request.getHeader(AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            token.substring(BEARER_PREFIX.length)
        } else null
    }
}
