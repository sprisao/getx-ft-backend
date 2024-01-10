package kr.getx.fitnessteachers.security

import kr.getx.fitnessteachers.security.handler.CustomAuthenticationSuccessHandler
import kr.getx.fitnessteachers.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
class SecurityConfig(val jwtTokenProvider: JwtTokenProvider) {
    private val allowedUrls = arrayOf("/*", "api/*", "/api/users/add", "/api/users/*", "/api/**", "/login", "/users", "/health")
    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf { it.disable() }
        .authorizeHttpRequests {
            it.requestMatchers(*allowedUrls).permitAll()    // 허용할 url 목록을 배열로 분리했다
                .anyRequest().authenticated()
        }
        .oauth2Login {
            // 로그인 성공시 추가작업 없이 이동
            // it.defaultSuccessUrl("/api/users/all")
            it.successHandler(CustomAuthenticationSuccessHandler(jwtTokenProvider))
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .build()!!
}