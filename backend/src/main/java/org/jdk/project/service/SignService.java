package org.jdk.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdk.project.dto.sign.SignInDto;
import org.jdk.project.dto.sign.SignUpDto;
import org.jdk.project.exception.BusinessException;
import org.jdk.project.repository.UserRepository;
import org.jooq.generated.project.tables.pojos.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 登录注册相关业务逻辑。 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SignService {

  private final UserRepository userRepository;

  /**
   * 用户登录。
   *
   * @param signInDto 登录请求（用户名/密码）
   * @return 登录成功后的用户ID
   */
  public Long signIn(SignInDto signInDto) {
    User user = userRepository.fetchEnabledUserByUsername(signInDto.getUsername());
    if (user == null) {
      throw new BusinessException("用户名或密码错误");
    }
    if (!signInDto.getPassword().equals(user.getPassword())) {
      throw new BusinessException("用户名或密码错误");
    }
    return user.getId();
  }

  /**
   * 用户注册，并绑定默认角色。
   *
   * @param signUpDto 注册请求（用户名/密码）
   */
  @Transactional(rollbackFor = Throwable.class)
  public void signUp(SignUpDto signUpDto) {
    // 用户名唯一性校验
    if (userRepository.fetchOneByUsername(signUpDto.getUsername()) != null) {
      throw new BusinessException("用户名已存在");
    }
    User user = new User();
    user.setUsername(signUpDto.getUsername());
    user.setPassword(signUpDto.getPassword());
    user.setEnable(true);
    userRepository.insert(user);
  }
}
