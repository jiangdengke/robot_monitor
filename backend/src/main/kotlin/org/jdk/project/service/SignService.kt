package org.jdk.project.service

import org.jdk.project.dto.sign.SignInDto
import org.jdk.project.dto.sign.SignUpDto
import org.jdk.project.exception.BusinessException
import org.jdk.project.repository.UserRepository
import org.jooq.generated.project.tables.pojos.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignService(
    private val userRepository: UserRepository,
) {
    fun signIn(signInDto: SignInDto): Long? {
        val user = userRepository.fetchEnabledUserByUsername(signInDto.username)
            ?: throw BusinessException("用户名或密码错误")
        if (signInDto.password != user.password) {
            throw BusinessException("用户名或密码错误")
        }
        return user.id
    }

    @Transactional(rollbackFor = [Throwable::class])
    fun signUp(signUpDto: SignUpDto) {
        if (userRepository.fetchOneByUsername(signUpDto.username) != null) {
            throw BusinessException("用户名已存在")
        }
        val user = User().apply {
            username = signUpDto.username
            password = signUpDto.password
            enable = true
        }
        userRepository.insert(user)
    }
}
