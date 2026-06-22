package org.jdk.project.dto

import jakarta.annotation.Nullable

/**
 * 分页响应 DTO。
 *
 * @param T 数据类型
 */
data class PageResponseDto<T>(
  var total: Long,
  @field:Nullable @param:Nullable var data: T?,
) {
  init {
    if (total < 0) {
      throw IllegalArgumentException("total must not be less than zero")
    }
  }

  companion object {
    /** 返回一个空分页结果。 */
    @JvmStatic
    fun <T> empty(): PageResponseDto<T> {
      return PageResponseDto(0, null)
    }
  }
}
