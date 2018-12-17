package com.assisteddarwinism.ultimatesmashtracker.player

import com.assisteddarwinism.ultimatesmashtracker.auth.TokenValidator
import com.assisteddarwinism.ultimatesmashtracker.auth.repository.token.TokenRepository
import com.assisteddarwinism.ultimatesmashtracker.model.exception.ResourceNotFoundException
import com.assisteddarwinism.ultimatesmashtracker.player.model.Player
import com.assisteddarwinism.ultimatesmashtracker.player.repository.PlayerDTO
import com.assisteddarwinism.ultimatesmashtracker.player.repository.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/players")
class PlayerController {

    @Autowired
    lateinit var tokenValidator: TokenValidator

    @Autowired
    lateinit var playerRepository: PlayerRepository

    @Autowired
    lateinit var tokenRepository: TokenRepository

    @GetMapping
    fun getPlayers(@RequestHeader("X-AuthToken") token: String): List<Player> {
        tokenValidator.checkTokenValid(token)

        return playerRepository.findAll().map { it -> Player(it.id!!, it.name) }
    }

    @GetMapping("/{playerId}")
    fun getPlayerDetails(@RequestHeader("X-AuthToken") token: String, @PathVariable("playerId") playerId: Long): Player {
        tokenValidator.checkTokenValid(token)
        try {
            return getPlayers(token).first { it.id == playerId }
        } catch (e: NoSuchElementException) {
            throw ResourceNotFoundException()
        }
        // TODO: calculate wins, losses, and characters
    }

    @GetMapping("/me")
    fun getMe(@RequestHeader("X-AuthToken") token: String): Player {
        tokenValidator.checkTokenValid(token)
        var tokenDTO = tokenRepository.findTokenDTOByToken(token)
        return getPlayers(token).first { it.id == tokenDTO.id }
    }

    @GetMapping("/{playerId}/matches")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    fun getPlayerMatches(@RequestHeader("X-AuthToken") token: String, @PathVariable("playerId") playerId: Long) {
        tokenValidator.checkTokenValid(token)
        var me = getMe(token)

        // TODO:
    }

    @DeleteMapping("/{playerId}")
    fun deletePlayer(@RequestHeader("X-AuthToken") token: String, @PathVariable("playerId") playerId: Long) {
        tokenValidator.checkTokenAdmin(token)

        if (!playerRepository.existsById(playerId)) throw ResourceNotFoundException()
        val storedPlayer = playerRepository.findById(playerId).get()
        if (storedPlayer.isDeleted) throw ResourceNotFoundException()

        val updatedPlayer = PlayerDTO(storedPlayer.name, storedPlayer.admin, true, storedPlayer.id)
        playerRepository.save(updatedPlayer)
    }

    @PutMapping("/{playerId}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    fun updatePlayer(@RequestHeader("X-TokenAuth") token: String, @PathVariable("playerId") playerId: Long) {
        tokenValidator.checkTokenAdmin(token)
    }
}