package com.assisteddarwinism.ultimatesmashtracker.auth

import com.assisteddarwinism.ultimatesmashtracker.auth.model.exception.InsufficientPrivilegesException
import com.assisteddarwinism.ultimatesmashtracker.auth.model.exception.InvalidCredentialsException
import com.assisteddarwinism.ultimatesmashtracker.auth.model.exception.TokenExpiredException
import com.assisteddarwinism.ultimatesmashtracker.auth.repository.token.TokenRepository
import com.assisteddarwinism.ultimatesmashtracker.player.repository.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenValidator {

    @Autowired
    lateinit var tokenRepository: TokenRepository

    @Autowired
    lateinit var playerRepository: PlayerRepository

    fun checkTokenValid(token: String) {
        if(!tokenRepository.existsByToken(token)) throw InvalidCredentialsException()
        val storedToken = tokenRepository.findTokenDTOByToken(token)
        if(storedToken.expiry.before(Date())) throw TokenExpiredException()
    }

    fun checkTokenAdmin(token: String) {
        checkTokenValid(token)
        val storedToken = tokenRepository.findTokenDTOByToken(token)
        val player = playerRepository.findById(storedToken.id)
        if(!player.get().admin) throw InsufficientPrivilegesException()
    }

}