package org.jdk.project.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
  @NotBlank private String username;
  @NotBlank private String password;
  private String nickname;
  private String email;
  private String phone;
  private String sex;
  private String avatarUrl;
  private Boolean enable;
  private String remark;
}
