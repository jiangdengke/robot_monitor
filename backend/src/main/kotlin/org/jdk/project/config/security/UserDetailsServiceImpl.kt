package org.jdk.project.config.security

import org.jdk.project.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(id: String): UserDetails {
        val dbUser =
            userRepository.findUserById(id.toLong())
                ?: throw UsernameNotFoundException(String.format("uid %s user not found", id))
        return User(
            dbUser.username,
            dbUser.password,
            dbUser.enable == null || dbUser.enable,
            true,
            true,
            true,
            emptySet<SimpleGrantedAuthority>(),
        )
    }
}
