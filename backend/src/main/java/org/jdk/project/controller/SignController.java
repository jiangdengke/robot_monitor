package org.jdk.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jdk.project.config.security.Jwt;
import org.jdk.project.dto.sign.SignInDto;
import org.jdk.project.dto.sign.SignUpDto;
import org.jooq.generated.project.tables.pojos.User;
import org.jdk.project.service.SignService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.Map;
import org.jdk.project.repository.UserRepository;

/** 认证接口：登录、注册、登出。 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignController {

  private final SignService signService;

  private final Jwt jwt;

  private final UserRepository userRepository;

  /**
   * 登录并下发 JWT 到 Cookie。
   *
   * @param request HTTP 请求
   * @param response HTTP 响应
   * @param signInDto 登录参数
   */
  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/login")
  Map<String, String> login(
      HttpServletRequest request,
      HttpServletResponse response,
      @RequestBody @Valid SignInDto signInDto) {
    Long userId = signService.signIn(signInDto);
    String token = jwt.create(String.valueOf(userId));
    jwt.makeToken(request, response, String.valueOf(userId));
    return Map.of("token", token);
  }

  /**
   * 注册新用户。
   *
   * @param signUpDto 注册参数
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/sign-up")
  void signUp(@RequestBody @Valid SignUpDto signUpDto) {
    signService.signUp(signUpDto);
  }

  /**
   * 登出并清除 JWT Cookie。
   *
   * @param request HTTP 请求
   * @param response HTTP 响应
   */
  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/sign-out")
  void signOut(HttpServletRequest request, HttpServletResponse response) {
    jwt.removeToken(request, response);
  }

  /**
   * 获取当前登录用户的基础信息。
   */
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/me")
  public User me(Principal principal) {
    if (principal == null) return null;
    User user = userRepository.fetchOneByUsername(principal.getName());
    if (user != null) {
      user.setPassword(null);
    }
    return user;
  }
}
