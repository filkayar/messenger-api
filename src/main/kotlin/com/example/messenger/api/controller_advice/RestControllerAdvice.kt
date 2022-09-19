package com.example.messenger.api.controller_advice

import com.example.messenger.api.constants.ErrorResponse
import com.example.messenger.api.constants.ResponseConstants
import com.example.messenger.api.exceptions.UserDeactivatedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestControllerAdvice {
    @ExceptionHandler(UserDeactivatedException::class)
    fun userDeactivated(userDeactivatedException: UserDeactivatedException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.ACCOUNT_DEACTIVATED.value, userDeactivatedException.message)
        // Возврат ответа ошибка HTTP 403 неудачной авторизации
        return ResponseEntity(res, HttpStatus.UNAUTHORIZED)
    }
}