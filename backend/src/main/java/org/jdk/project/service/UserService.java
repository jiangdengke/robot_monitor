package org.jdk.project.service;

import static org.jooq.generated.project.tables.User.USER;

import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.user.CreateUserRequest;
import org.jdk.project.dto.user.UpdateProfileRequest;
import org.jdk.project.dto.user.UpdateUserRequest;
import org.jdk.project.dto.user.UserDto;
import org.jdk.project.exception.BusinessException;
import org.jdk.project.repository.UserRepository;
import org.jooq.DSLContext;
import org.jooq.generated.project.tables.pojos.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private static final DateTimeFormatter DATETIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private final DSLContext dsl;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public ListResponse<UserDto> listUsers() {
    List<UserDto> rows =
        dsl.selectFrom(USER).orderBy(USER.ID.asc()).fetch(record -> toDto(record.into(User.class)));
    return ListResponse.of(rows.size(), rows);
  }

  public UserDto getUser(Long id) {
    User user = userRepository.fetchOneById(id);
    if (user == null) {
      throw new BusinessException("用户不存在");
    }
    return toDto(user);
  }

  @Transactional
  public UserDto createUser(CreateUserRequest request) {
    if (userRepository.fetchOneByUsername(request.getUsername()) != null) {
      throw new BusinessException("用户名已存在");
    }
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setNickname(defaultString(request.getNickname(), request.getUsername()));
    user.setEmail(defaultString(request.getEmail(), ""));
    user.setPhone(defaultString(request.getPhone(), ""));
    user.setSex(defaultString(request.getSex(), "2"));
    user.setAvatarUrl(defaultString(request.getAvatarUrl(), ""));
    user.setEnable(request.getEnable() == null || request.getEnable());
    user.setRemark(defaultString(request.getRemark(), ""));
    userRepository.insert(user);
    return toDto(userRepository.fetchOneByUsername(request.getUsername()));
  }

  @Transactional
  public UserDto updateUser(UpdateUserRequest request) {
    User user = userRepository.fetchOneById(request.getId());
    if (user == null) {
      throw new BusinessException("用户不存在");
    }
    if (request.getNickname() != null) user.setNickname(request.getNickname());
    if (request.getEmail() != null) user.setEmail(request.getEmail());
    if (request.getPhone() != null) user.setPhone(request.getPhone());
    if (request.getSex() != null) user.setSex(request.getSex());
    if (request.getAvatarUrl() != null) user.setAvatarUrl(request.getAvatarUrl());
    if (request.getEnable() != null) user.setEnable(request.getEnable());
    if (request.getRemark() != null) user.setRemark(request.getRemark());
    userRepository.update(user);
    return toDto(userRepository.fetchOneById(request.getId()));
  }

  @Transactional
  public void deleteUsers(List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      return;
    }
    dsl.deleteFrom(USER).where(USER.ID.in(ids)).execute();
  }

  public UserDto getProfile(String username) {
    User user = userRepository.fetchOneByUsername(username);
    if (user == null) {
      throw new BusinessException("用户不存在");
    }
    return toDto(user);
  }

  @Transactional
  public UserDto updateProfile(String username, UpdateProfileRequest request) {
    User user = userRepository.fetchOneByUsername(username);
    if (user == null) {
      throw new BusinessException("用户不存在");
    }
    if (request.getNickname() != null) user.setNickname(request.getNickname());
    if (request.getEmail() != null) user.setEmail(request.getEmail());
    if (request.getPhone() != null) user.setPhone(request.getPhone());
    if (request.getSex() != null) user.setSex(request.getSex());
    userRepository.update(user);
    return toDto(userRepository.fetchOneByUsername(username));
  }

  @Transactional
  public void updatePassword(String username, String oldPassword, String newPassword) {
    User user = userRepository.fetchOneByUsername(username);
    if (user == null) {
      throw new BusinessException("用户不存在");
    }
    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
      throw new BusinessException("旧密码错误");
    }
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.update(user);
  }

  @Transactional
  public UserDto updateAvatar(String username, String avatarUrl) {
    User user = userRepository.fetchOneByUsername(username);
    if (user == null) {
      throw new BusinessException("用户不存在");
    }
    user.setAvatarUrl(defaultString(avatarUrl, ""));
    userRepository.update(user);
    return toDto(userRepository.fetchOneByUsername(username));
  }

  private UserDto toDto(User user) {
    return UserDto.builder()
        .id(user.getId())
        .username(user.getUsername())
        .nickname(user.getNickname())
        .email(user.getEmail())
        .phone(user.getPhone())
        .sex(user.getSex())
        .avatarUrl(user.getAvatarUrl())
        .enable(user.getEnable())
        .remark(user.getRemark())
        .createTime(formatDateTime(user.getCreateTime()))
        .updateTime(formatDateTime(user.getUpdateTime()))
        .build();
  }

  private String formatDateTime(java.time.OffsetDateTime value) {
    return value == null ? null : value.toLocalDateTime().format(DATETIME_FORMATTER);
  }

  private String defaultString(String value, String fallback) {
    return value == null ? fallback : value;
  }
}
