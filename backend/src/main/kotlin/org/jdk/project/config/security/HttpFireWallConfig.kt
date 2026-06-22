package org.jdk.project.config.security

import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.security.web.firewall.HttpFirewall
import org.springframework.security.web.firewall.RequestRejectedHandler
import org.springframework.security.web.firewall.StrictHttpFirewall

@Configuration
class HttpFireWallConfig {
    @Bean
    fun getHttpFirewall(): HttpFirewall = StrictHttpFirewall()

    @Bean
    fun requestRejectedHandler(): RequestRejectedHandler =
        RequestRejectedHandler { _, response, requestRejectedException ->
            response.status = HttpServletResponse.SC_BAD_REQUEST
            response.contentType = MediaType.TEXT_PLAIN_VALUE
            response.writer.use { writer -> writer.write(requestRejectedException.message) }
        }
}
