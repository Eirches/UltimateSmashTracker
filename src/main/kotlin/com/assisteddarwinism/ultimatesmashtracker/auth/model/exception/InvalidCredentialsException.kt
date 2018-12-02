package com.assisteddarwinism.ultimatesmashtracker.auth.model.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class InvalidCredentialsException() : Exception()