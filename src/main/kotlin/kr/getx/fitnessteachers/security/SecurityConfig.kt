package kr.getx.fitnessteachers.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
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
    private val allowedUrls = arrayOf(
        "/**"
    )
    val jwtTokenFilter = JwtTokenFilter(JwtUtils())
    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf { it.disable() }
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests {
            it.requestMatchers(*allowedUrls).permitAll()    // 허용할 url 목록을 배열로 분리했다
                .anyRequest().authenticated()
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) } // 세션을 사용할 수 있게 변경
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java) // JwtTokenFilter 추가
        // OAuth2 인증 프로세스에 필요한 세션 정보를 유지하기 위해 필요
        .build()!!

    @Bean
    fun corsConfigurationSource() : CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}