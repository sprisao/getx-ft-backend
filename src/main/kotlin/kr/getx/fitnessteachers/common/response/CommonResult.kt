package kr.getx.fitnessteachers.common.response

import lombok.Getter
import lombok.Setter

/**
 * packageName   : kr.getx.fitnessteachers.common.response
 * fileName  : CommonResult
 * author    : jiseung-gu
 * date  : 2024/01/05
 * description :
 **/
open class CommonResult {
  // 응답 코드
  var code = 0

  // 응답 메시지
  var msg: String? = null
}
