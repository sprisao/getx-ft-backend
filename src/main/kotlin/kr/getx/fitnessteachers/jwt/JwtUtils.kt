package kr.getx.fitnessteachers.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.SignatureException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtUtils {
    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            // 로그: 서명 검증 실패
        } catch (e: MalformedJwtException) {
            // 로그: 구조적 문제가 있는 JWT
        } catch (e: ExpiredJwtException) {
            // 로그: 만료된 JWT
        } catch (e: UnsupportedJwtException) {
            // 로그: 지원되지 않는 JWT
        } catch (e: IllegalArgumentException) {
            // 로그: 부적절한 인자
        }
        return false
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }
}
