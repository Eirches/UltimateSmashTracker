package com.assisteddarwinism.ultimatesmashtracker.auth.repository.token

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Tokens")
data class TokenDTO(@Id val id: Long, val token: String, val expiry: Date)