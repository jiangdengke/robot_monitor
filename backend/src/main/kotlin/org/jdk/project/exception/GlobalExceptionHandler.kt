package org.jdk.project.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.firewall.RequestRejectedException
import org.springframework.web.ErrorResponseException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [BusinessException::class])
    fun handleBusinessException(ex: BusinessException, request: WebRequest): ResponseEntity<Any>? {
        log.error("Business Error Handled  ===> ", ex)
        val errorResponseException =
            ErrorResponseException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.message),
                ex.cause,
            )
        return handleExceptionInternal(
            errorResponseException,
            errorResponseException.body,
            errorResponseException.headers,
            errorResponseException.statusCode,
            request,
        )
    }

    @Nullable
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        log.error("MethodArgumentNotValidException Handled  ===> ", ex)
        val errorResponseException =
            ErrorResponseException(
                status,
                ProblemDetail.forStatusAndDetail(status, ex.message),
                ex.cause,
            )
        return handleExceptionInternal(
            errorResponseException,
            errorResponseException.body,
            errorResponseException.headers,
            errorResponseException.statusCode,
            request,
        )
    }

    @ExceptionHandler(value = [RequestRejectedException::class])
    fun handleRequestRejectedException(ex: RequestRejectedException, request: WebRequest): ResponseEntity<Any>? {
        log.error("RequestRejectedException Handled  ===> ", ex)
        val errorResponseException =
            ErrorResponseException(
                HttpStatus.BAD_REQUEST,
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.message),
                ex.cause,
            )
        return handleExceptionInternal(
            errorResponseException,
            errorResponseException.body,
            errorResponseException.headers,
            errorResponseException.statusCode,
            request,
        )
    }

    @ExceptionHandler(value = [AccessDeniedException::class])
    fun handleAccessDenied(ex: AccessDeniedException): ResponseEntity<Any> {
        throw ex
    }

    @ExceptionHandler(value = [Throwable::class])
    fun handleException(ex: Throwable, request: WebRequest): ResponseEntity<Any>? {
        log.error("System Error Handled  ===> ", ex)
        val errorResponseException =
            ErrorResponseException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.message),
                ex.cause,
            )
        return handleExceptionInternal(
            errorResponseException,
            errorResponseException.body,
            errorResponseException.headers,
            errorResponseException.statusCode,
            request,
        )
    }

    private companion object {
        private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
}
