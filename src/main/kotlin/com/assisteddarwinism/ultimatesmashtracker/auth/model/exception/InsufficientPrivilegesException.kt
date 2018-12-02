package com.assisteddarwinism.ultimatesmashtracker.auth.model.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception

@ResponseStatus(HttpStatus.FORBIDDEN)
class InsufficientPrivilegesException : Exception()