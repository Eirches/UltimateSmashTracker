package com.assisteddarwinism.ultimatesmashtracker.auth.repository.token

import org.springframework.data.repository.CrudRepository

interface TokenRepository : CrudRepository<TokenDTO, Long> {
    fun findTokenDTOByToken(token: String): TokenDTO
    fun existsByToken(token: String): Boolean
}

