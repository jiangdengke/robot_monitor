package org.jdk.project.controller

import java.security.Principal
import org.jdk.project.dto.user.UpdateProfileRequest
import org.jdk.project.dto.user.UserDto
import org.jdk.project.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/me")
class ProfileController(
    private val userService: UserService,
) {
    @GetMapping
    fun getProfile(principal: Principal): UserDto = userService.getProfile(principal.name)

    @PutMapping
    fun updateProfile(principal: Principal, @RequestBody request: UpdateProfileRequest): UserDto =
        userService.updateProfile(principal.name, request)

    @PutMapping("/password")
    fun updatePassword(
        principal: Principal,
        @RequestParam oldPassword: String,
        @RequestParam newPassword: String,
    ) {
        userService.updatePassword(principal.name, oldPassword, newPassword)
    }

    @PutMapping("/avatar")
    fun updateAvatar(principal: Principal, @RequestParam avatarUrl: String): UserDto =
        userService.updateAvatar(principal.name, avatarUrl)
}
