package kr.getx.fitnessteachers.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(private val env: Environment) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val isDev = env.activeProfiles.contains("dev")

        http.csrf { csrf ->
            csrf.disable()
        }

        if (isDev) {
            http.authorizeHttpRequests { authorize ->
                authorize.anyRequest().permitAll()
            }
        } else {
            http.authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/**").permitAll()
                    .anyRequest().authenticated()
            }
        }

        http.sessionManagement { session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*") // 필요한 포트 추가
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)

        return source
    }
}