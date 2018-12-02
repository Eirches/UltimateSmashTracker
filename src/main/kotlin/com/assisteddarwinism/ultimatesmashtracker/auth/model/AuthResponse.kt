package com.assisteddarwinism.ultimatesmashtracker.auth.model

import java.util.*

data class AuthResponse(val id: Long, val authToken: String, val expiryTime: Date)