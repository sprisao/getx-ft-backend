package kr.getx.fitnessteachers.service

import kr.getx.fitnessteachers.dto.JobPostDto
import kr.getx.fitnessteachers.dto.UserDto
import kr.getx.fitnessteachers.entity.JobPostFavorite
import kr.getx.fitnessteachers.repository.JobPostFavoriteRepository
import org.springframework.stereotype.Service
@Service
class JobPostFavoriteService(
    private val jobPostFavoriteRepository: JobPostFavoriteRepository,
    private val jobPostService: JobPostService,
    private val userService: UserService,
) {
    fun addFavorite(userId: Int, jobPostId: Int): JobPostFavorite {
        val jobPost = jobPostService.findById(jobPostId)
            ?: throw IllegalArgumentException("해당 구직 공고가 존재하지 않습니다.")
        val user = userService.findUserById(userId)
            ?: throw IllegalArgumentException("해당 사용자가 존재하지 않습니다.")

        return jobPostFavoriteRepository.save(JobPostFavorite(jobPost = jobPost, user = user))
    }

    fun cancelFavorite(userId: Int, jobPostId: Int) {
        val favorite = jobPostFavoriteRepository.findByUserUserIdAndJobPostJobPostId(userId, jobPostId)
           ?: throw IllegalArgumentException("해당 사용자가 해당 구직 공고를 즐겨찾기한 내역이 존재하지 않습니다.")
        jobPostFavoriteRepository.delete(favorite)
    }

    fun getFavoritedJobPosts(userId: Int): List<JobPostDto> =
        jobPostFavoriteRepository.findByUserUserId(userId).map {
           JobPostDto.fromEntity(it.jobPost)
        }
    fun getFavoritedUsers(jobPostId: Int): List<UserDto> =
        jobPostFavoriteRepository.findByJobPostJobPostId(jobPostId).map {
            UserDto.fromEntity(it.user)
        }

    fun getFavoritedUserCount(jobPostId: Int): Int =
        jobPostFavoriteRepository.countByJobPostJobPostId(jobPostId)
}