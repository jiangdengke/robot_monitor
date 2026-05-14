package org.jdk.project.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
  @NotNull private Long id;
  private String nickname;
  private String email;
  private String phone;
  private String sex;
  private String avatarUrl;
  private Boolean enable;
  private String remark;
}
