package com.assisteddarwinism.ultimatesmashtracker.auth

import com.assisteddarwinism.ultimatesmashtracker.auth.model.AuthData
import com.assisteddarwinism.ultimatesmashtracker.auth.model.AuthResponse
import com.assisteddarwinism.ultimatesmashtracker.auth.model.MakeAdminRequest
import com.assisteddarwinism.ultimatesmashtracker.auth.model.exception.InvalidCredentialsException
import com.assisteddarwinism.ultimatesmashtracker.auth.model.exception.UsernameInUseException
import com.assisteddarwinism.ultimatesmashtracker.auth.repository.password.PasswordDTO
import com.assisteddarwinism.ultimatesmashtracker.auth.repository.password.PasswordRepository
import com.assisteddarwinism.ultimatesmashtracker.auth.repository.token.TokenDTO
import com.assisteddarwinism.ultimatesmashtracker.auth.repository.token.TokenRepository
import com.assisteddarwinism.ultimatesmashtracker.model.exception.ResourceNotFoundException
import com.assisteddarwinism.ultimatesmashtracker.player.repository.PlayerDTO
import com.assisteddarwinism.ultimatesmashtracker.player.repository.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*

@RestController
class AuthController {

    @Value("\${tokenExpirySeconds}")
    var expirySeconds: Int = 600

    @Autowired
    lateinit var sskValidator: SSKValidator

    @Autowired
    lateinit var playerRepository: PlayerRepository

    @Autowired
    lateinit var passwordRepository: PasswordRepository

    @Autowired
    lateinit var tokenRepository: TokenRepository

    @Autowired
    lateinit var tokenValidator: TokenValidator

    @PostMapping("/auth")
    fun authorize(@RequestHeader("X-SSK") ssk: String, @RequestBody authData: AuthData): AuthResponse {
        sskValidator.validateSSK(ssk)

        //Check if username is present
        if (!playerRepository.existsByName(authData.name)) throw InvalidCredentialsException()
        val player = playerRepository.findByName(authData.name)
        if (player.isDeleted) throw InvalidCredentialsException()

        //Check if password matches username's value
        val passwordInfo = passwordRepository.findById(player.id!!).get()
        val hashedPw = saltAndHash(passwordInfo.salt, authData.password)
        if (hashedPw != passwordInfo.password) throw InvalidCredentialsException()

        //Generate auth token
        val authToken: String = UUID.randomUUID().toString()
        var expiryDate = Calendar.getInstance()
        expiryDate.time = Date()
        expiryDate.add(Calendar.SECOND, expirySeconds)
        val tokenDTO = TokenDTO(player.id, authToken, expiryDate.time)
        tokenRepository.save(tokenDTO)

        return AuthResponse(player.id, "$authToken", expiryDate.time)
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestHeader("X-SSK") ssk: String, @RequestBody authData: AuthData) {
        sskValidator.validateSSK(ssk)

        //Make sure username isn't already taken
        if (playerRepository.existsByName(authData.name)) throw UsernameInUseException()

        //Create new user information
        val playerDTO = PlayerDTO(authData.name)
        val finalPlayer = playerRepository.save(playerDTO)

        //Make the first person to register into an admin
        if (finalPlayer.id == 1L) {
            val adminDTO = PlayerDTO(finalPlayer.name, true, false, finalPlayer.id)
            playerRepository.save(adminDTO)
        }

        //Store password data
        val salt = UUID.randomUUID().toString()
        val saltedHashedPassword = saltAndHash(salt, authData.password)
        val passwordDTO = PasswordDTO(finalPlayer.id!!, saltedHashedPassword, salt)
        passwordRepository.save(passwordDTO)
    }

    @PostMapping("/admin")
    fun makeAdmin(@RequestHeader("X-AuthToken") token: String, @RequestBody makeAdminRequest: MakeAdminRequest) {
        tokenValidator.checkTokenAdmin(token)

        if (!playerRepository.existsById(makeAdminRequest.playerId)) throw ResourceNotFoundException()
        val storedPlayer = playerRepository.findById(makeAdminRequest.playerId).get()
        val updatedPlayer = PlayerDTO(storedPlayer.name, true, storedPlayer.isDeleted, storedPlayer.id)
        playerRepository.save(updatedPlayer)
    }

    @DeleteMapping("/admin/{playerId}")
    fun removeAdmin(@RequestHeader("X-AuthToken") token: String, @PathVariable("playerId") playerId: Long) {
        tokenValidator.checkTokenAdmin(token)

        if (!playerRepository.existsById(playerId)) throw ResourceNotFoundException()
        val storedPlayer = playerRepository.findById(playerId).get()
        val updatedPlayer = PlayerDTO(storedPlayer.name, false, storedPlayer.isDeleted, storedPlayer.id)
        playerRepository.save(updatedPlayer)
    }


    private fun saltAndHash(salt: String, value: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(value.toByteArray(Charset.defaultCharset()))
        digest.update(salt.toByteArray(Charset.defaultCharset()))
        return String(digest.digest(), Charset.defaultCharset())
    }
}