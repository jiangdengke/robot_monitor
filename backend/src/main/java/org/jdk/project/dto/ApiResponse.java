package org.jdk.project.dto;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

  private final int code;
  private final String msg;
  private final T data;

  private ApiResponse(int code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public static <T> ApiResponse<T> ok(T data, String message) {
    return new ApiResponse<>(200, message, data);
  }
}
