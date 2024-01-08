package kr.getx.fitnessteachers.repository

import kr.getx.fitnessteachers.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Int>{
    // 최초 소셜 로그인 가입자인지 확인
    // userSocialMediaId 를 검색을 통해서 이미 있는 사용자인지 확인
    fun findByUserSocialMediaId(userSocialMediaId: String): User?

    // 중복 이메일 체크
    // 이미 생성된 사용자인지 처음 가입하는 사용자인지 확인
    fun findByEmailAndProvider(email: String?, provider: String?): Optional<User?>?
}