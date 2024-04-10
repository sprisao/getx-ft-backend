package kr.getx.fitnessteachers.controller

import kr.getx.fitnessteachers.service.WorkDayService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/workdays")
class WorkDayController(
    private val workDayService: WorkDayService
) {

}