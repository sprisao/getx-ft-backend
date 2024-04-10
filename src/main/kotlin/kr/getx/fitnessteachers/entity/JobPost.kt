package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime


@Entity
@Table(name = "jobPosts")
data class JobPost(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val jobPostId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "centerId", referencedColumnName = "centerId")
    val center: Center,

    // 작성 전, 작성 완료
    var isPostCompleted: Boolean = false,

    // 모집중, 모집마감
    var isRecruitmentOpen : Boolean = false,

    // 모집 직종 ( 요갸, 필라테스, 트레이너 )
    @ElementCollection
    var jobCategories: List<String> = emptyList(),

    // 근무 위치
    var workLocation: String,

    // 근무 요일, 시간대 List배열
    @OneToMany(mappedBy = "jobPost", cascade = [CascadeType.ALL])
    var workDays: List<WorkDay> = emptyList(),

    // 고용 형태 ( 정규직, 계약직, 대강, 직접입력 )
    var employmentType: String,

    // 입금 유형 ( 타임제, 연봉 )
    var salaryType: String,

    // 급여
    var salary : String,

    // 기본급 유무 ( 있음, 없음 )
    var hasBaseSalary: Boolean,

    // 경력 요건 ( 경력 N년 이상 )
    var experienceLevel: Int,

    // 제2외국어 가능 여부
    var isSecondLanguageAvailable: Boolean = false,

    // 전공 학위 필요 여부
    var isMajorDegreeRequired: Boolean = false,

    // 모집 인원
    var numberOfPositions: Int,

    // 자격 요건
    @Column(length = 10000)
    var qualifications: String,

    // 우대 사항
    @Column(length = 10000)
    var preferences: String,

    // 간단 소개글
    @Column(length = 10000)
    var details: String,

    // 지원 기간 종료일
    var applicationPeriodEnd: LocalDate? = null,

    // 근무 시작일
    var workStartDate: LocalDate?,

    @CreationTimestamp
    val postedDate: LocalDateTime = LocalDateTime.now()
)