package org.jdk.project.controller

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.user.CreateUserRequest
import org.jdk.project.dto.user.UpdateUserRequest
import org.jdk.project.dto.user.UserDto
import org.jdk.project.service.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {
    @GetMapping
    fun listUsers(): ListResponse<UserDto> = userService.listUsers()

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserDto = userService.getUser(id)

    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest): UserDto = userService.createUser(request)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody request: UpdateUserRequest): UserDto {
        request.id = id
        return userService.updateUser(request)
    }

    @DeleteMapping("/{ids}")
    fun deleteUsers(@PathVariable ids: String) {
        val userIds =
            ids.split(",")
                .filter { it.isNotBlank() }
                .map { it.toLong() }
        userService.deleteUsers(userIds)
    }
}
