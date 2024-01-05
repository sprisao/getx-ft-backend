package kr.getx.fitnessteachers.common.response

/**
 * packageName   : kr.getx.fitnessteachers.common.response
 * fileName  : ListResult
 * author    : jiseung-gu
 * date  : 2024/01/05
 * description :
 **/
class ListResult<T> : CommonResult() {
  var data: List<T>? = null
}
