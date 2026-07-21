package org.jdk.project.service

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.user.CreateUserRequest
import org.jdk.project.dto.user.UpdateProfileRequest
import org.jdk.project.dto.user.UpdateUserRequest
import org.jdk.project.dto.user.UserDto
import org.jdk.project.exception.BusinessException
import org.jdk.project.repository.UserRepository
import org.jdk.project.repository.UserRow
import org.jdk.project.repository.UserWriteData
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun listUsers(): ListResponse<UserDto> {
        val rows = userRepository.findAllUsers().map(::toDto)
        return ListResponse.of(rows.size.toLong(), rows)
    }

    fun getUser(id: Long): UserDto {
        val user = userRepository.findUserById(id) ?: throw BusinessException("用户不存在")
        return toDto(user)
    }

    @Transactional
    fun createUser(request: CreateUserRequest): UserDto {
        if (userRepository.findUserByUsername(request.username) != null) {
            throw BusinessException("用户名已存在")
        }
        userRepository.insertUser(
            UserWriteData(
                username = request.username,
                nickname = defaultString(request.nickname, request.username),
                email = defaultString(request.email, ""),
                phone = defaultString(request.phone, ""),
                sex = defaultString(request.sex, "2"),
                avatarUrl = defaultString(request.avatarUrl, ""),
                password = request.password,
                enable = request.enable ?: true,
                remark = defaultString(request.remark, ""),
            ),
        )
        return toDto(userRepository.findUserByUsername(request.username) ?: throw BusinessException("用户不存在"))
    }

    @Transactional
    fun updateUser(request: UpdateUserRequest): UserDto {
        val userId = request.id ?: throw BusinessException("用户不存在")
        val existingUser = userRepository.findUserById(userId) ?: throw BusinessException("用户不存在")
        userRepository.updateUser(
            userId,
            existingUser.toWriteData(
                nickname = request.nickname ?: existingUser.nickname,
                email = request.email ?: existingUser.email,
                phone = request.phone ?: existingUser.phone,
                sex = request.sex ?: existingUser.sex,
                avatarUrl = request.avatarUrl ?: existingUser.avatarUrl,
                password = if (request.password.isNullOrBlank()) existingUser.password else request.password,
                enable = request.enable ?: existingUser.enable,
                remark = request.remark ?: existingUser.remark,
            ),
        )
        return toDto(userRepository.findUserById(userId) ?: throw BusinessException("用户不存在"))
    }

    @Transactional
    fun deleteUsers(ids: List<Long>?) {
        if (ids.isNullOrEmpty()) {
            return
        }
        userRepository.deleteUsersByIds(ids)
    }

    fun getProfile(username: String): UserDto {
        val user = userRepository.findUserByUsername(username) ?: throw BusinessException("用户不存在")
        return toDto(user)
    }

    @Transactional
    fun updateProfile(
        username: String,
        request: UpdateProfileRequest,
    ): UserDto {
        val existingUser = userRepository.findUserByUsername(username) ?: throw BusinessException("用户不存在")
        val userId = existingUser.id ?: throw BusinessException("用户不存在")
        userRepository.updateUser(
            userId,
            existingUser.toWriteData(
                nickname = request.nickname ?: existingUser.nickname,
                email = request.email ?: existingUser.email,
                phone = request.phone ?: existingUser.phone,
                sex = request.sex ?: existingUser.sex,
            ),
        )
        return toDto(userRepository.findUserById(userId) ?: throw BusinessException("用户不存在"))
    }

    @Transactional
    fun updatePassword(
        username: String,
        oldPassword: String,
        newPassword: String,
    ) {
        val existingUser = userRepository.findUserByUsername(username) ?: throw BusinessException("用户不存在")
        if (oldPassword != existingUser.password) {
            throw BusinessException("旧密码错误")
        }
        val userId = existingUser.id ?: throw BusinessException("用户不存在")
        userRepository.updateUser(userId, existingUser.toWriteData(password = newPassword))
    }

    @Transactional
    fun updateAvatar(
        username: String,
        avatarUrl: String?,
    ): UserDto {
        val existingUser = userRepository.findUserByUsername(username) ?: throw BusinessException("用户不存在")
        val userId = existingUser.id ?: throw BusinessException("用户不存在")
        userRepository.updateUser(userId, existingUser.toWriteData(avatarUrl = defaultString(avatarUrl, "")))
        return toDto(userRepository.findUserById(userId) ?: throw BusinessException("用户不存在"))
    }

    private fun toDto(user: UserRow): UserDto =
        UserDto(
            id = user.id,
            username = user.username,
            nickname = user.nickname,
            email = user.email,
            phone = user.phone,
            sex = user.sex,
            avatarUrl = user.avatarUrl,
            enable = user.enable,
            remark = user.remark,
            createTime = formatDateTime(user.createTime),
            updateTime = formatDateTime(user.updateTime),
        )

    private fun formatDateTime(value: OffsetDateTime?): String? = value?.toLocalDateTime()?.format(DATETIME_FORMATTER)

    private fun defaultString(
        value: String?,
        fallback: String?,
    ): String? = value ?: fallback

    private fun UserRow.toWriteData(
        nickname: String? = this.nickname,
        email: String? = this.email,
        phone: String? = this.phone,
        sex: String? = this.sex,
        avatarUrl: String? = this.avatarUrl,
        password: String? = this.password,
        enable: Boolean? = this.enable,
        remark: String? = this.remark,
    ): UserWriteData =
        UserWriteData(
            username = username,
            nickname = nickname,
            email = email,
            phone = phone,
            sex = sex,
            avatarUrl = avatarUrl,
            password = password,
            enable = enable,
            remark = remark,
        )

    private companion object {
        private val DATETIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
}
