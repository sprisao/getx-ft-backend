package kr.getx.fitnessteachers.dto

import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.entity.WorkDay

data class WorkDayDto(
    val workDayId: Int = 0,
    var jobPostId: Int,
    val day: String,
    val startTime: String,
    val endTime: String
) {
    companion object {
        fun fromEntity(workDay: WorkDay): WorkDayDto = WorkDayDto(
            workDayId = workDay.workDayId,
            jobPostId = workDay.jobPost.jobPostId,
            day = workDay.day,
            startTime = workDay.startTime,
            endTime = workDay.endTime,
        )
    }

    fun toEntity(jobPost: JobPost): WorkDay = WorkDay(
        jobPost = jobPost,
        day = day,
        startTime = startTime,
        endTime = endTime,
    )
}
