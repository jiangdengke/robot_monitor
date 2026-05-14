package org.jdk.project.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {
  private String nickname;
  private String email;
  private String phone;
  private String sex;
}
