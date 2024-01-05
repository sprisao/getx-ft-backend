package kr.getx.fitnessteachers.common.service

import kr.getx.fitnessteachers.common.response.CommonResult
import kr.getx.fitnessteachers.common.response.ListResult
import kr.getx.fitnessteachers.common.response.SingleResult
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.util.*

/**
 * packageName   : kr.getx.fitnessteachers.common.service
 * fileName  : ResponseService
 * author    : jiseung-gu
 * date  : 2024/01/05
 * description :
 **/
@Service
class ResponseService {

  enum class CommonResponse(val code: Int, val msg: String) {
    SUCCESS(0, "성공하였습니다."),
    FAIL(-1, "실패하였습니다.");
  }
  // 단일건 결과를 처리하는 메소드
  fun <T> getSingleResult(data: T): SingleResult<T> {
    val result: SingleResult<T> = SingleResult()
    val log = KotlinLogging.logger {}
    log.info { "getUser data : $data" }
    result.data = data
    setSuccessResult(result)
    return result
  }
  // 다중건 결과를 처리하는 메소드
  fun <T> getListResult(list: List<T>?): ListResult<T> {
    val result: ListResult<T> = ListResult()
    result.data = list
    setSuccessResult(result)
    return result
  }

  // 성공 결과만 처리하는 메소드
  fun getSuccessResult(): CommonResult?{
    val result: CommonResult = CommonResult()
    setSuccessResult(result)
    return result
  }

  // 실패 결과만 처리하는 메소드 - 시스템 에러인 경우만 사용할것!
  fun getFailResult(code: Int, msg: String?): CommonResult? {
    val result = CommonResult()
    // result.setSuccess(false);
    result.code = CommonResponse.FAIL.code
    result.msg = msg ?: CommonResponse.FAIL.msg
    return result
  }

  // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
  private fun setSuccessResult(result: CommonResult) {
    result.code= CommonResponse.SUCCESS.code
    result.msg = CommonResponse.SUCCESS.msg
  }
}
