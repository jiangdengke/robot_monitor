package org.jdk.project.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
  private Long id;
  private String username;
  private String nickname;
  private String email;
  private String phone;
  private String sex;
  private String avatarUrl;
  private Boolean enable;
  private String remark;
  private String createTime;
  private String updateTime;
}
