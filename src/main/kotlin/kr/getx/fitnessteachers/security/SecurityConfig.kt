package kr.getx.fitnessteachers.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
class SecurityConfig {
    private val allowedUrls = arrayOf("/*", "/login", "/users", "/health", "/actuator/**")

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .authorizeHttpRequests {
            it.requestMatchers(*allowedUrls).permitAll()    // 허용할 url 목록을 배열로 분리했다
                .anyRequest().authenticated()
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .build()!!
}