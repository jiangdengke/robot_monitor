package org.jdk.project.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.jdk.project.config.security.Jwt
import org.jdk.project.dto.sign.AuthUserDto
import org.jdk.project.dto.sign.SignInDto
import org.jdk.project.dto.sign.SignInResponse
import org.jdk.project.dto.sign.SignUpDto
import org.jdk.project.service.SignService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/auth")
class SignController(
    private val signService: SignService,
    private val jwt: Jwt,
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    fun login(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @RequestBody @Valid signInDto: SignInDto,
    ): SignInResponse {
        val userId = signService.signIn(signInDto).toString()
        val token = jwt.create(userId)
        jwt.makeToken(request, response, userId)
        return SignInResponse(token)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody @Valid signUpDto: SignUpDto,
    ) {
        signService.signUp(signUpDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/sign-out")
    fun signOut(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {
        jwt.removeToken(request, response)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    fun me(principal: Principal?): AuthUserDto? =
        principal?.let { authenticatedPrincipal -> signService.getCurrentUser(authenticatedPrincipal.name) }
}
