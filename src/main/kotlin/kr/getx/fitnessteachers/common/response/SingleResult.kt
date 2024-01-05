package kr.getx.fitnessteachers.common.response

import lombok.Getter
import lombok.Setter

/**
 * packageName   : kr.getx.fitnessteachers.common.response
 * fileName  : SingleResult
 * author    : jiseung-gu
 * date  : 2024/01/05
 * description :
 **/

class SingleResult<T> : CommonResult() {
  var data: T? = null
}
