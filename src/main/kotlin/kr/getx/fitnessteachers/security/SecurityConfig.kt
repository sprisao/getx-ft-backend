package kr.getx.fitnessteachers.security

import kr.getx.fitnessteachers.security.handler.CustomAuthenticationSuccessHandler
import kr.getx.fitnessteachers.security.jwt.JwtTokenProvider
import kr.getx.fitnessteachers.security.oauth2.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
class SecurityConfig(val jwtTokenProvider: JwtTokenProvider, val customOAuth2UserService: CustomOAuth2UserService) {
    private val allowedUrls = arrayOf("/*", "/api/users/all", "/api/users/add", "/api/users/*", "/api/**", "/login", "/users", "/health")
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
            it.userInfoEndpoint { it.userService(customOAuth2UserService) }
            it.successHandler(CustomAuthenticationSuccessHandler(jwtTokenProvider))
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) } // 세션을 사용할 수 있게 변경
        // OAuth2 인증 프로세스에 필요한 세션 정보를 유지하기 위해 필요
        .build()!!
}