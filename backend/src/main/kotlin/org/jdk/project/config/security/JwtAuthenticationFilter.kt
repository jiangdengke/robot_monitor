package org.jdk.project.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwt: Jwt,
    private val userDetailsService: UserDetailsServiceImpl,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token = jwt.extract(request)
        if (StringUtils.isNotEmpty(token) && jwt.verify(token)) {
            try {
                val userDetails = userDetailsService.loadUserByUsername(jwt.getSubject(token!!))
                val authenticated = JwtAuthenticationToken.authenticated(userDetails, token, userDetails.authorities)
                authenticated.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticated
            } catch (exception: Exception) {
                log.error("jwt with invalid user id {}", jwt.getSubject(token!!), exception)
            }
        }
        filterChain.doFilter(request, response)
    }

    private companion object {
        private val log = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)
    }
}
