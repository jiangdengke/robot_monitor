package org.jdk.project.controller;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.user.UpdateProfileRequest;
import org.jdk.project.dto.user.UserDto;
import org.jdk.project.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class ProfileController {

  private final UserService userService;

  @GetMapping
  public UserDto getProfile(Principal principal) {
    return userService.getProfile(principal.getName());
  }

  @PutMapping
  public UserDto updateProfile(
      Principal principal, @RequestBody UpdateProfileRequest request) {
    return userService.updateProfile(principal.getName(), request);
  }

  @PutMapping("/password")
  public void updatePassword(
      Principal principal,
      @RequestParam String oldPassword,
      @RequestParam String newPassword) {
    userService.updatePassword(principal.getName(), oldPassword, newPassword);
  }

  @PutMapping("/avatar")
  public UserDto updateAvatar(
      Principal principal, @RequestParam String avatarUrl) {
    return userService.updateAvatar(principal.getName(), avatarUrl);
  }
}
