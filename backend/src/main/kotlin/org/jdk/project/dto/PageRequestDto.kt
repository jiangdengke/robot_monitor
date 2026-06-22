package org.jdk.project.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.Locale
import java.util.regex.Pattern
import org.jdk.project.utils.StringCaseUtils.convertCamelCaseToSnake
import org.jooq.SortField
import org.jooq.SortOrder
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.name

/** 分页请求 DTO，包含页码、页大小与排序信息。 */
class PageRequestDto {
  var page: Long = 0
  var size: Long = 0

  @field:Schema(description = "排序字段", example = "name:asc,age:desc", type = "string")
  var sortBy: Map<String, Direction> = HashMap()

  /** 构造分页请求（无排序）。 */
  constructor(page: Int, size: Int) {
    checkPageAndSize(page, size)
    this.page = page.toLong()
    this.size = size.toLong()
  }

  /** 构造分页请求（包含排序）。 */
  constructor(page: Int, size: Int, sortBy: Map<String, Direction>) {
    checkPageAndSize(page, size)
    this.page = page.toLong()
    this.size = size.toLong()
    this.sortBy = sortBy
  }

  constructor()

  enum class Direction(val keyword: String) {
    ASC("ASC"),
    DESC("DESC"),
    ;

    companion object {
      /** 从字符串解析排序方向。 */
      @JvmStatic
      fun fromString(value: String?): Direction {
        try {
          return valueOf(value!!.uppercase(Locale.US))
        } catch (e: Exception) {
          throw IllegalArgumentException(
            String.format(
              "Invalid value '%s' for orders given; Has to be either 'desc' or 'asc' (case insensitive)",
              value,
            ),
            e,
          )
        }
      }
    }
  }

  /** 生成 jOOQ 的排序字段列表，默认为 id desc。 */
  fun getSortFields(): List<SortField<Any>> {
    val sortFields =
      sortBy.entries.map { entry ->
        field(name(convertCamelCaseToSnake(entry.key))).sort(SortOrder.valueOf(entry.value.keyword))
      }
    return if (sortFields.isEmpty()) {
      listOf(field(name("id")).sort(SortOrder.DESC))
    } else {
      sortFields
    }
  }

  /** 计算偏移量（分页 offset）。 */
  fun getOffset(): Long {
    return if (page == 0L) {
      0
    } else {
      (page - 1) * size
    }
  }

  /** 从字符串设置排序，示例：name:asc,age:desc。 */
  fun setSortBy(sortBy: String?) {
    this.sortBy = convertSortBy(sortBy)
  }

  /** 校验页码与页大小。 */
  private fun checkPageAndSize(page: Int, size: Int) {
    if (page < 0) {
      throw IllegalArgumentException("Page index must not be less than zero")
    }

    if (size < 1) {
      throw IllegalArgumentException("Page size must not be less than one")
    }
  }

  /** 将排序字符串解析为 Map。 */
  private fun convertSortBy(sortBy: String?): Map<String, Direction> {
    val result = HashMap<String, Direction>()
    if (sortBy.isNullOrEmpty()) {
      return result
    }
    for (fieldSpaceDirection in sortBy.split(",")) {
      val fieldDirectionArray = fieldSpaceDirection.split(COLON)
      if (fieldDirectionArray.size != 2) {
        throw IllegalArgumentException(
          String.format(
            "Invalid sortBy field format %s. The expect format is [col1 asc,col2 desc]",
            sortBy,
          ),
        )
      }
      val field = fieldDirectionArray[0]
      if (!verifySortField(field)) {
        throw IllegalArgumentException(
          String.format("Invalid Sort field %s. Sort field must match %s", sortBy, REGEX),
        )
      }
      val direction = fieldDirectionArray[1]
      result[field] = Direction.fromString(direction)
    }
    return result
  }

  companion object {
    const val REGEX = "^[a-zA-Z][a-zA-Z0-9_]*$"
    const val COLON = ":"

    /** 工厂方法：无排序。 */
    @JvmStatic
    fun of(page: Int, size: Int): PageRequestDto {
      return PageRequestDto(page, size)
    }

    /** 工厂方法：包含排序。 */
    @JvmStatic
    fun of(page: Int, size: Int, sortBy: Map<String, Direction>): PageRequestDto {
      return PageRequestDto(page, size, sortBy)
    }

    /** 校验排序字段（只能字母数字下划线，且不能以数字开头）。 */
    private fun verifySortField(sortField: String): Boolean {
      val pattern = Pattern.compile(REGEX)
      val matcher = pattern.matcher(sortField)
      return matcher.matches()
    }
  }
}
