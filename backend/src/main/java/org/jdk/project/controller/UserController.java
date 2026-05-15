package org.jdk.project.controller;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.user.CreateUserRequest;
import org.jdk.project.dto.user.UpdateUserRequest;
import org.jdk.project.dto.user.UserDto;
import org.jdk.project.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public ListResponse<UserDto> listUsers() {
    return userService.listUsers();
  }

  @GetMapping("/{id}")
  public UserDto getUser(@PathVariable Long id) {
    return userService.getUser(id);
  }

  @PostMapping
  public UserDto createUser(@RequestBody CreateUserRequest request) {
    return userService.createUser(request);
  }

  @PutMapping("/{id}")
  public UserDto updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
    request.setId(id);
    return userService.updateUser(request);
  }

  @DeleteMapping("/{ids}")
  public void deleteUsers(@PathVariable String ids) {
    List<Long> userIds =
        Arrays.stream(ids.split(","))
            .filter(item -> !item.isBlank())
            .map(Long::valueOf)
            .toList();
    userService.deleteUsers(userIds);
  }
}
