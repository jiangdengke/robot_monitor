package org.jdk.project.dto;

import java.util.List;
import lombok.Getter;

/** 通用列表返回。 */
@Getter
public class ListResponse<T> {
  private final long total;
  private final List<T> rows;

  public ListResponse(long total, List<T> rows) {
    this.total = total;
    this.rows = rows;
  }

  public static <T> ListResponse<T> of(long total, List<T> rows) {
    return new ListResponse<>(total, rows);
  }
}
