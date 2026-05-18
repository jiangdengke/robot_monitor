package org.jdk.project.dto.sign;

import lombok.Getter;

@Getter
public class SignInResponse {
  private final String token;

  public SignInResponse(String token) {
    this.token = token;
  }
}
