package com.assisteddarwinism.ultimatesmashtracker.auth

import com.assisteddarwinism.ultimatesmashtracker.auth.model.exception.InvalidCredentialsException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SSKValidator {

    @Value("\${SSK}")
    private val validSSK: String? = null

    private fun isSSKValid(ssk: String) = validSSK?.equals(ssk) ?: false

    fun validateSSK(ssk: String) {
        if (!isSSKValid(ssk)) throw InvalidCredentialsException()
    }
}