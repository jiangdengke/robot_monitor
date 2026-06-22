package org.jdk.project.dto

/** 通用列表返回。 */
class ListResponse<T>(
  val total: Long,
  val rows: List<T>,
) {
  companion object {
    @JvmStatic
    fun <T> of(total: Long, rows: List<T>): ListResponse<T> {
      return ListResponse(total, rows)
    }
  }
}
