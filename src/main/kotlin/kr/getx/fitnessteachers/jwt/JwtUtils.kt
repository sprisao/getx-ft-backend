package kr.getx.fitnessteachers.jwt

import com.nimbusds.jose.util.StandardCharset
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import kr.getx.fitnessteachers.dto.UserDto
import kr.getx.fitnessteachers.entity.TeacherType
import org.springframework.stereotype.Component

@Component
class JwtUtils {

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor("s3BTt6uIdU6/99xZQYfOQoh4cRrJyZIXSbrmq+4nDog".toByteArray(StandardCharset.UTF_8)))
                .build()
                .parseClaimsJws(authToken)
            return true
        } catch (e: JwtException) {
            // 로그: Jwt 관련 예외 처리 (일괄 처리)
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

    // Jwt 토큰 추출
    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }

    // Jwt Claims 추출
    fun getSocialLoginInfo(authToken: String): UserDto {
        val claims = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor("s3BTt6uIdU6/99xZQYfOQoh4cRrJyZIXSbrmq+4nDog".toByteArray(StandardCharset.UTF_8)))
            .build()
            .parseClaimsJws(authToken)
            .body

        val email = claims["email"] as? String
        val name = claims["name"] as? String
        val socialType = claims["socialType"] as String

        return if (email != null && name != null) {
            UserDto(0, name, "", email, socialType, false, "", false, TeacherType.FITNESS, false, false, false)
        } else {
            throw Exception("Invalid Token")
        }
    }
}
