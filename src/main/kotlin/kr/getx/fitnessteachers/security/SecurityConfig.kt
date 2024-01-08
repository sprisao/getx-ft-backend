package kr.getx.fitnessteachers.security

import kr.getx.fitnessteachers.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

@Configuration
class SecurityConfig(
        private val oAuthService: OAuthService,
    ) {
    private val allowedUrls = arrayOf("/login/oauth2/code/naver", "/oauth2/**", "/login/**", "/error", "/health")

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf { it.disable() }
        .authorizeHttpRequests {
            it.requestMatchers(*allowedUrls).permitAll()
                .anyRequest().authenticated()
        }
        .oauth2Login {
            it.successHandler(MyAuthenticationSuccessHandler)
            it.defaultSuccessUrl("/api/users/logininfo")
            it.failureUrl("/")
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) }
        .build()!!

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.addAllowedOrigin("http://localhost:8080")
        configuration.addAllowedMethod("*")
        configuration.addAllowedHeader("*")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
