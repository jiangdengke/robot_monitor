package org.jdk.project.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@Component
class Jwt(
    @Value("\${jwt.secret}") val secret: String,
    @Value("\${jwt.expiration-min}") val expirationMin: Int,
    @Value("\${jwt.cookie-name}") val cookieName: String,
) {
    private val verifier: JWTVerifier = JWT.require(Algorithm.HMAC256(secret)).build()

    fun getSubject(token: String): String = JWT.decode(token).subject

    fun verify(token: String?): Boolean =
        try {
            verifier.verify(token)
            true
        } catch (exception: JWTVerificationException) {
            false
        }

    fun extract(request: HttpServletRequest): String? {
        val authorization = request.getHeader("Authorization")
        if (StringUtils.isNotBlank(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7).trim()
        }
        return WebUtils.getCookie(request, cookieName)?.value
    }

    fun create(userIdentify: String): String =
        JWT.create()
            .withSubject(userIdentify)
            .withIssuedAt(Date())
            .withExpiresAt(
                Date.from(
                    LocalDateTime.now()
                        .plusMinutes(expirationMin.toLong())
                        .atZone(ZoneId.systemDefault())
                        .toInstant(),
                ),
            ).sign(Algorithm.HMAC256(secret))

    private fun buildJwtCookiePojo(
        request: HttpServletRequest,
        userIdentify: String,
    ): Cookie {
        val contextPath = request.contextPath
        val cookiePath = if (StringUtils.isNotEmpty(contextPath)) contextPath else "/"
        return Cookie(cookieName, create(userIdentify)).apply {
            path = cookiePath
            maxAge = expirationMin * 60
            secure = request.isSecure
            isHttpOnly = true
        }
    }

    fun makeToken(
        request: HttpServletRequest,
        response: HttpServletResponse,
        userIdentify: String,
    ) {
        response.addCookie(buildJwtCookiePojo(request, userIdentify))
    }

    fun removeToken(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {
        val contextPath = request.contextPath
        val cookiePath = if (StringUtils.isNotEmpty(contextPath)) contextPath else "/"
        val expired = Cookie(cookieName, "")
        expired.path = cookiePath
        expired.maxAge = 0
        expired.isHttpOnly = true
        expired.secure = request.isSecure
        response.addCookie(expired)
    }
}
