package kr.getx.fitnessteachers.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import kr.getx.fitnessteachers.jwt.JwtTokenFilter
import kr.getx.fitnessteachers.jwt.JwtUtils
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig {
    private val allowedUrls = arrayOf("/**")
    val jwtTokenFilter = JwtTokenFilter(JwtUtils())

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf { it.disable() } // CSRF 보호 기능을 비활성화합니다.
        .cors { it.configurationSource(corsConfigurationSource()) } // CORS 설정을 적용합니다.
        .authorizeHttpRequests {
            it.requestMatchers(*allowedUrls).permitAll() // allowedUrls 배열에 정의된 경로는 인증 없이 접근 가능합니다.
                .anyRequest().authenticated() // 그 외의 모든 요청은 인증이 필요합니다.
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) } // 세션은 필요한 경우에만 생성됩니다.
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java) // JWT 토큰 필터를 UsernamePasswordAuthenticationFilter 전에 추가합니다.
        .build()!!

    @Bean
    fun corsConfigurationSource() : CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("https://www.pilyo.xyz") // 이 출처에서의 요청만 허용합니다.
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS") // 이 HTTP 메서드들을 허용합니다.
        configuration.allowedHeaders = listOf("Authorization", "Content-Type") // 이 헤더들을 허용합니다.
        configuration.allowCredentials = true // 인증 정보를 포함한 요청을 허용합니다.
        configuration.maxAge = 3600L // CORS 프리플라이트 요청의 캐시 시간을 1시간으로 설정합니다.
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration) // 모든 경로에 대해 이 CORS 설정을 적용합니다.
        return source
    }
}