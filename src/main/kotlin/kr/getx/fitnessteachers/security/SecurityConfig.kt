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
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig {
    private val allowedUrls = arrayOf("/**")
    val jwtTokenFilter = JwtTokenFilter(JwtUtils())

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { csrf ->
            csrf.disable()
        }

        http.cors { cors ->
            cors.configurationSource(corsConfigurationSource())
        }

        http.authorizeHttpRequests { authorize ->
            authorize
                .requestMatchers(*allowedUrls).permitAll()
                .anyRequest().authenticated()
        }

        http.sessionManagement { session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("https://www.pilyo.xyz")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type")
        configuration.allowCredentials = true
        configuration.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)

        return source
    }
}