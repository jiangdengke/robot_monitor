package org.jdk.project.utils

/** 字符串大小写与命名格式工具。 */
object StringCaseUtils {
  /**
   * 将驼峰命名转换为下划线命名。
   *
   * @param input 输入的驼峰字符串
   * @return 下划线格式字符串
   */
  @JvmStatic
  fun convertCamelCaseToSnake(input: String): String {
    val result = StringBuilder()
    for (c in input.toCharArray()) {
      if (Character.isUpperCase(c)) {
        result.append("_").append(Character.toLowerCase(c))
      } else {
        result.append(c)
      }
    }
    return result.toString()
  }
}
