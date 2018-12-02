package com.assisteddarwinism.ultimatesmashtracker.model.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException : Exception()