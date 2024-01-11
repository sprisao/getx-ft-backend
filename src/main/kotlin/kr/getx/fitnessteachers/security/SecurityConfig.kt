package kr.getx.fitnessteachers.security

import kr.getx.fitnessteachers.security.jwt.JwtAuthorizationFilter
import kr.getx.fitnessteachers.oauth2.HttpCookieOAuth2AuthorizationRequestRepository
import kr.getx.fitnessteachers.oauth2.handler.OAuth2AuthenticationFailureHandler
import kr.getx.fitnessteachers.oauth2.handler.OAuth2AuthenticationSuccessHandler
import kr.getx.fitnessteachers.oauth2.service.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
    private val oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler,
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
    private val jwtAuthorizationFilter: JwtAuthorizationFilter
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .headers { it.frameOptions { it.disable() } } // For H2 DB
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .authorizeHttpRequests { requests ->
                requests.requestMatchers("/api/hello/**", "/h2-console/**").permitAll()
                requests.anyRequest().authenticated()
            }
            .sessionManagement { sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .oauth2Login { configure ->
                configure.authorizationEndpoint { config -> config.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository) }
                    .userInfoEndpoint { config -> config.userService(customOAuth2UserService) }
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler)
            }
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
