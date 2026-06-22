package org.jdk.project.service

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.user.CreateUserRequest
import org.jdk.project.dto.user.UpdateProfileRequest
import org.jdk.project.dto.user.UpdateUserRequest
import org.jdk.project.dto.user.UserDto
import org.jdk.project.exception.BusinessException
import org.jdk.project.repository.UserRepository
import org.jooq.DSLContext
import org.jooq.generated.project.tables.User.USER
import org.jooq.generated.project.tables.pojos.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val dsl: DSLContext,
    private val userRepository: UserRepository,
) {
    fun listUsers(): ListResponse<UserDto> {
        val rows = dsl.selectFrom(USER).orderBy(USER.ID.asc()).fetch { record -> toDto(record.into(User::class.java)) }
        return ListResponse.of(rows.size.toLong(), rows)
    }

    fun getUser(id: Long): UserDto {
        val user = userRepository.fetchOneById(id) ?: throw BusinessException("用户不存在")
        return toDto(user)
    }

    @Transactional
    fun createUser(request: CreateUserRequest): UserDto {
        if (userRepository.fetchOneByUsername(request.username) != null) {
            throw BusinessException("用户名已存在")
        }
        val user = User().apply {
            username = request.username
            password = request.password
            nickname = defaultString(request.nickname, request.username)
            email = defaultString(request.email, "")
            phone = defaultString(request.phone, "")
            sex = defaultString(request.sex, "2")
            avatarUrl = defaultString(request.avatarUrl, "")
            enable = request.enable ?: true
            remark = defaultString(request.remark, "")
        }
        userRepository.insert(user)
        return toDto(userRepository.fetchOneByUsername(request.username) ?: throw BusinessException("用户不存在"))
    }

    @Transactional
    fun updateUser(request: UpdateUserRequest): UserDto {
        val userId = request.id ?: throw BusinessException("用户不存在")
        val user = userRepository.fetchOneById(userId) ?: throw BusinessException("用户不存在")
        request.nickname?.let { user.nickname = it }
        request.email?.let { user.email = it }
        request.phone?.let { user.phone = it }
        request.sex?.let { user.sex = it }
        request.avatarUrl?.let { user.avatarUrl = it }
        if (!request.password.isNullOrBlank()) {
            user.password = request.password
        }
        request.enable?.let { user.enable = it }
        request.remark?.let { user.remark = it }
        userRepository.update(user)
        return toDto(userRepository.fetchOneById(userId) ?: throw BusinessException("用户不存在"))
    }

    @Transactional
    fun deleteUsers(ids: List<Long>?) {
        if (ids.isNullOrEmpty()) {
            return
        }
        dsl.deleteFrom(USER).where(USER.ID.`in`(ids)).execute()
    }

    fun getProfile(username: String): UserDto {
        val user = userRepository.fetchOneByUsername(username) ?: throw BusinessException("用户不存在")
        return toDto(user)
    }

    @Transactional
    fun updateProfile(username: String, request: UpdateProfileRequest): UserDto {
        val user = userRepository.fetchOneByUsername(username) ?: throw BusinessException("用户不存在")
        request.nickname?.let { user.nickname = it }
        request.email?.let { user.email = it }
        request.phone?.let { user.phone = it }
        request.sex?.let { user.sex = it }
        userRepository.update(user)
        return toDto(userRepository.fetchOneByUsername(username) ?: throw BusinessException("用户不存在"))
    }

    @Transactional
    fun updatePassword(username: String, oldPassword: String, newPassword: String) {
        val user = userRepository.fetchOneByUsername(username) ?: throw BusinessException("用户不存在")
        if (oldPassword != user.password) {
            throw BusinessException("旧密码错误")
        }
        user.password = newPassword
        userRepository.update(user)
    }

    @Transactional
    fun updateAvatar(username: String, avatarUrl: String?): UserDto {
        val user = userRepository.fetchOneByUsername(username) ?: throw BusinessException("用户不存在")
        user.avatarUrl = defaultString(avatarUrl, "")
        userRepository.update(user)
        return toDto(userRepository.fetchOneByUsername(username) ?: throw BusinessException("用户不存在"))
    }

    private fun toDto(user: User): UserDto =
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

    private fun defaultString(value: String?, fallback: String?): String? = value ?: fallback

    private companion object {
        private val DATETIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
}
