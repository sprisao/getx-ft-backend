package kr.getx.fitnessteachers.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Date
import org.springframework.stereotype.Service

@Service
class JwtTokenProvider {
    private val secretKey = "pilyo" // 보안을 위해 환경변수로부터 가져오는 것이 좋습니다.

    fun createToken(username: String, roles: List<String>): String {
        val claims = Jwts.claims().setSubject(username)
        claims["roles"] = roles

        val now = Date()
        val validity = Date(now.time + 3600000) // 예: 1시간

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.expiration.after(Date())
    }
}
