package kr.getx.fitnessteachers.security.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.slf4j.LoggerFactory
import java.security.Key
import java.util.*

@Component
class TokenProvider {

    companion object {
        private const val ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS: Long = 1000 * 60 * 30 // 30min
        private val log = LoggerFactory.getLogger(TokenProvider::class.java)
    }

    @Value("\${jwt.secret}")
    private lateinit var secret: String
    private lateinit var key: Key

    @PostConstruct
    fun init() {
        val keyBytes = io.jsonwebtoken.io.Decoders.BASE64URL.decode(secret)
        this.key = Keys.hmacShaKeyFor(keyBytes)
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: UnsupportedJwtException) {
            log.error("JWT is not valid")
            false
        } catch (e: MalformedJwtException) {
            log.error("JWT is not valid")
            false
        } catch (e: SignatureException) {
            log.error("JWT signature validation fails")
            false
        } catch (e: ExpiredJwtException) {
            log.error("JWT is expired")
            false
        } catch (e: IllegalArgumentException) {
            log.error("JWT is null or empty or only whitespace")
            false
        } catch (e: Exception) {
            log.error("JWT validation fails", e)
            false
        }
    }

    fun createToken(authentication: Authentication): String {
        val date = Date()
        val expiryDate = Date(date.time + ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS)

        return Jwts.builder()
            .setSubject(authentication.name)
            .setIssuedAt(date)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        val user: UserDetails = User(claims.subject, "", emptyList())

        return UsernamePasswordAuthenticationToken(user, "", emptyList())
    }
}
