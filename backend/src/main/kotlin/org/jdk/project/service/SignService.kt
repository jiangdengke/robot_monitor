package org.jdk.project.service

import org.jdk.project.dto.sign.AuthUserDto
import org.jdk.project.dto.sign.SignInDto
import org.jdk.project.dto.sign.SignUpDto
import org.jdk.project.exception.BusinessException
import org.jdk.project.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignService(
    private val userRepository: UserRepository,
) {
    fun signIn(signInDto: SignInDto): Long? {
        val user =
            userRepository.findEnabledUserByUsername(signInDto.username)
                ?: throw BusinessException("用户名或密码错误")
        if (signInDto.password != user.password) {
            throw BusinessException("用户名或密码错误")
        }
        return user.id
    }

    @Transactional(rollbackFor = [Throwable::class])
    fun signUp(signUpDto: SignUpDto) {
        if (userRepository.findUserByUsername(signUpDto.username) != null) {
            throw BusinessException("用户名已存在")
        }
        userRepository.insertCredentials(signUpDto.username, signUpDto.password)
    }

    fun getCurrentUser(username: String): AuthUserDto? =
        userRepository.findUserByUsername(username)?.let { user ->
            AuthUserDto(
                id = user.id,
                username = user.username,
                nickname = user.nickname,
                email = user.email,
                phone = user.phone,
                sex = user.sex,
                avatarUrl = user.avatarUrl,
                createTime = user.createTime,
                updateTime = user.updateTime,
                password = null,
                enable = user.enable,
                remark = user.remark,
            )
        }
}
