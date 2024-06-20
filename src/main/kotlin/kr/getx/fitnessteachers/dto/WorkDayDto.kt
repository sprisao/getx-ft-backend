package kr.getx.fitnessteachers.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import kr.getx.fitnessteachers.entity.JobPost
import kr.getx.fitnessteachers.entity.WorkDay

data class WorkDayDto(
    val workDayId: Int = 0,
    @JsonProperty("jobPostId") var jobPostId: Int,
    val day: String,
    val startTime: String,
    val endTime: String
) {
    @JsonCreator
    constructor(
        @JsonProperty("jobPostId") jobPostId: Int,
        @JsonProperty("day") day: String,
        @JsonProperty("startTime") startTime: String,
        @JsonProperty("endTime") endTime: String
    ) : this(0, jobPostId, day, startTime, endTime)

    companion object {
        fun fromEntity(workDay: WorkDay): WorkDayDto = WorkDayDto(
            workDayId = workDay.workDayId,
            jobPostId = workDay.jobPost.jobPostId,
            day = workDay.day,
            startTime = workDay.startTime,
            endTime = workDay.endTime
        )
    }

    fun toEntity(jobPost: JobPost): WorkDay = WorkDay(
        jobPost = jobPost,
        day = day,
        startTime = startTime,
        endTime = endTime
    )
}