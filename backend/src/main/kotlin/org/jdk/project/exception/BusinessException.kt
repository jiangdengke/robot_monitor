package org.jdk.project.exception

/** 业务异常。 */
class BusinessException : RuntimeException {
  constructor() : super()

  /** 指定消息的构造。 */
  constructor(message: String?) : super(message)

  /** 指定消息与原因的构造。 */
  constructor(message: String?, cause: Throwable?) : super(message, cause)

  /** 指定原因的构造。 */
  constructor(cause: Throwable?) : super(cause)

  /** 完整参数构造。 */
  constructor(
    message: String?,
    cause: Throwable?,
    enableSuppression: Boolean,
    writableStackTrace: Boolean,
  ) : super(message, cause, enableSuppression, writableStackTrace)

  companion object {
    @java.io.Serial
    private const val serialVersionUID: Long = -2119302295305964305L
  }
}
