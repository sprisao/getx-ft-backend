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

    // 모집 직종 ( 요가, 필라테스, 트레이너 )
    var jobCategories: String,

    // 근무 위치
    var workLocation: String,

    // 근무 요일, 시간대 List배열
    @OneToMany(mappedBy = "jobPost", cascade = [CascadeType.ALL])
    var workDays: List<WorkDay> = emptyList(),

    // 고용 형태 ( 정규직, 계약직 )
    var employmentType: String,

    // 입금 유형 ( 타임제, 연봉 )
    var salaryType: String,

    // 임금 추가 사항 ( 옵션 )
    var additionalSalary: String,

    // 최소 급여
    var minSalary : Int,

    // 최대 급여
    var maxSalary : Int,

    // 경력 요건 ( 경력 N년 이상 )
    var experienceLevel: Int,

    // 모집 인원
    var numberOfPositions: Int,

    // 자격 요건
    var qualifications: String,

    // 우대 사항
    var preferences: String,

    // 간단 소개글
    var details: String,

    // 지원 기간 종료일
    var applicationPeriodEnd: LocalDate? = null,

    // 근무 시작일
    var workStartDate: LocalDate?,

    // isDeleted , SoftDelete
    var isDeleted: Boolean = false,

    var isDeletedAt: LocalDateTime? = null,

    @CreationTimestamp
    val postedDate: LocalDateTime = LocalDateTime.now()
)