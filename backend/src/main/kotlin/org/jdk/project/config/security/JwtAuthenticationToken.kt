package org.jdk.project.config.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.SpringSecurityCoreVersion
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serial

class JwtAuthenticationToken : AbstractAuthenticationToken {
    private val principalValue: Any?
    private var credentialsValue: String?

    constructor(principal: Any?, credentials: String?) : super(null) {
        principalValue = principal
        credentialsValue = credentials
        super.setAuthenticated(false)
    }

    constructor(
        principal: Any?,
        credentials: String?,
        authorities: Collection<GrantedAuthority>,
    ) : super(authorities) {
        principalValue = principal
        credentialsValue = credentials
        super.setAuthenticated(true)
    }

    override fun getCredentials(): String? = credentialsValue

    override fun getPrincipal(): Any? = principalValue

    companion object {
        @Serial
        private const val serialVersionUID: Long = SpringSecurityCoreVersion.SERIAL_VERSION_UID

        @JvmStatic
        fun unauthenticated(
            userIdentify: String?,
            token: String?,
        ): JwtAuthenticationToken = JwtAuthenticationToken(userIdentify, token)

        @JvmStatic
        fun authenticated(
            principal: UserDetails,
            token: String?,
            authorities: Collection<GrantedAuthority>,
        ): JwtAuthenticationToken = JwtAuthenticationToken(principal, token, authorities)
    }
}
