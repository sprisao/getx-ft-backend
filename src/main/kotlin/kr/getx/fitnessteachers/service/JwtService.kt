package kr.getx.fitnessteachers.service

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Value

@Service
class JwtService {

    @Value("\${jwt.secret}")
    private lateinit var secretKey: String
    fun createToken(userSocialMediaId : String, username: String): String {
        val now = System.currentTimeMillis()
        return Jwts.builder()
            .setSubject(userSocialMediaId)
            .claim("username", username)
            .setIssuedAt(java.util.Date(now))
            .setExpiration(java.util.Date(now + 1000 * 60 * 60))
            .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secretKey)
            .compact()
    }
}