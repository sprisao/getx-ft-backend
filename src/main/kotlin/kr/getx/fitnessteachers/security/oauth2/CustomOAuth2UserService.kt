package kr.getx.fitnessteachers.security.oauth2

import kr.getx.fitnessteachers.entity.User
import kr.getx.fitnessteachers.service.UserService
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.stereotype.Service


@Service
class CustomOAuth2UserService(private val userService: UserService) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private val delegate = DefaultOAuth2UserService()

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oauth2User = delegate.loadUser(userRequest)
        val attributes = oauth2User.attributes["response"] as? Map<String, Any?> ?: throw IllegalArgumentException("Invalid OAuth2 user data")
        val userSocialMediaId = attributes["id"] as String

        val user = User(
            userSocialMediaId = userSocialMediaId,
            email = attributes["email"] as String,
            username = attributes["name"] as String,
            phoneNumber = attributes["mobile"] as String
        )
        userService.addOrUpdateUser(user)

        return oauth2User
    }
}
