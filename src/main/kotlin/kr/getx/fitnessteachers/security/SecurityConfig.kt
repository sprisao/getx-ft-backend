package kr.getx.fitnessteachers.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
class SecurityConfig {
    private val allowedUrls = arrayOf("/*", "/api/users/all", "/api/users/login", "/api/users/*", "/api/**", "/login", "/users", "/health")
    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf { it.disable() }
        .authorizeHttpRequests {
            it.requestMatchers(*allowedUrls).permitAll()    // 허용할 url 목록을 배열로 분리했다
                .anyRequest().authenticated()
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) } // 세션을 사용할 수 있게 변경
        // OAuth2 인증 프로세스에 필요한 세션 정보를 유지하기 위해 필요
        .build()!!
}