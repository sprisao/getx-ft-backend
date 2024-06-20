//package kr.getx.fitnessteachers.entity
//
//import jakarta.persistence.EntityManager
//import jakarta.transaction.Transactional
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.beans.factory.annotation.Autowired
//import java.time.LocalDate
//
//
//@SpringBootTest
//@Transactional
//class JobPostTest {
//
//    @Autowired
//    private lateinit var entityManager: EntityManager
//
//    private lateinit var user: User
//    private lateinit var center: Center
//
//    @BeforeEach
//    fun setup() {
//        // User 엔티티 생성
//        user = User(
//            name = "홍길동",
//            nickname = "홍길동",
//            email = "honggildong@gmail.com",
//            socialType = "Naver",
//            photoIsDisplay = false,
//            photo = "",
//            userType = true,
//            userTypeStatus = true,
//            resumeExists = false,
//            centerExists = false,
//            isDeleted = false,
//        )
//        entityManager.persist(user)
//
//        // Center 엔티티 생성
//        center = Center(
//            user = user,
//            centerName = "피트니스센터",
//            roadAddress = "서울특별시 강남구 테헤란로 427",
//            sido = "서울특별시",
//            sidoEnglish = "Seoul",
//            sigungu = "강남구",
//            sigunguEnglish = "Gangnam-gu",
//        )
//        entityManager.persist(center)
//    }
//
//    @Test
//    fun `Create JobPost and save to database`() {
//        // given
//        val jobPost = JobPost(
//            center = center,
//            isPostCompleted = false,
//            isRecruitmentOpen = true,
//            jobCategories = "피트니스",
//            workLocation = "서울특별시 강남구 테헤란로 427",
//            workDays = emptyList(),
//            employmentType = "정규직",
//            salaryType = "월급",
//            additionalSalary = "없음",
//            minSalary = 2000000,
//            maxSalary = 3000000,
//            experienceLevel = 2,
//            numberOfPositions = 1,
//            qualifications = "운동지도자 자격증 소지자",
//            preferences = "피트니스 강사 경험자",
//            details = "피트니스 강사 경험자 우대",
//            workStartDate = LocalDate.of(2023, 7, 1),
//            title = "피트니스 강사 모집",
//            link = "https://www.naver.com",
//        )
//
//        // when
//        entityManager.persist(jobPost)
//        entityManager.flush()
//        entityManager.clear()
//
//        // then
//        val foundJobPost = entityManager.find(JobPost::class.java, jobPost.jobPostId)
//        assertEquals(jobPost, foundJobPost)
//    }
//
//}