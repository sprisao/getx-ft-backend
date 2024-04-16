package kr.getx.fitnessteachers.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "resumes")
data class Resume(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val resumeId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    val user: User,

    var description: String?,

    @Lob
    var photos: String?,

    var mainPhoto: String?,

    // 이력서 공개 여부
    var isDisplay: Boolean?,

    // 이력서 수정 상태
    var isEditing: Boolean?,

    // 교육 번호 적어놓기
    @ElementCollection
    @CollectionTable(name = "resume_education_ids", joinColumns = [JoinColumn(name = "resume_id")])
    @Column(name = "education_id")
    var educationIds: List<Int>? = null,

    // 경력 번호 적어놓기
    @ElementCollection
    @CollectionTable(name = "resume_experience_ids", joinColumns = [JoinColumn(name = "resume_id")])
    @Column(name = "experience_id")
    var experienceIds: List<Int>? = null,

    // 자격증 번호 적어놓기
    @ElementCollection
    @CollectionTable(name = "resume_certification_ids", joinColumns = [JoinColumn(name = "resume_id")])
    @Column(name = "certification_id")
    var certificationIds: List<Int>? = null,

    // 프로필 사진 적어놓기
    @ElementCollection
    @CollectionTable(name = "resumePhotoId", joinColumns = [JoinColumn(name = "resume_id")])
    @Column(name = "resumePhoto_id")
    var resumePhotoIds: List<Int>? = null,

    // 첨부파일 적어놓기
    @ElementCollection
    @CollectionTable(name = "resumeAttachmentId", joinColumns = [JoinColumn(name = "resume_id")])
    @Column(name = "resumeAttachment_id")
    var resumeAttachmentIds: List<Int>? = null,

    @CreationTimestamp
    val createdAt: LocalDateTime
)
