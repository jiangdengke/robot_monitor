package org.jdk.project.dto

class ApiResponse<T> private constructor(
  val code: Int,
  val msg: String,
  val data: T?,
) {
  companion object {
    @JvmStatic
    fun <T> ok(data: T?, message: String): ApiResponse<T> {
      return ApiResponse(200, message, data)
    }
  }
}
