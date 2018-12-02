package com.assisteddarwinism.ultimatesmashtracker.auth.repository.password

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Passwords")
data class PasswordDTO(@Id val id: Long, val password: String, val salt: String)

