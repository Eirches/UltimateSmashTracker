package com.assisteddarwinism.ultimatesmashtracker.player

import com.assisteddarwinism.ultimatesmashtracker.auth.TokenValidator
import com.assisteddarwinism.ultimatesmashtracker.match.model.Match
import com.assisteddarwinism.ultimatesmashtracker.match.model.PlayerCharacterRelation
import com.assisteddarwinism.ultimatesmashtracker.match.repository.MatchRepository
import com.assisteddarwinism.ultimatesmashtracker.model.exception.ResourceNotFoundException
import com.assisteddarwinism.ultimatesmashtracker.player.model.Player
import com.assisteddarwinism.ultimatesmashtracker.player.repository.PlayerDTO
import com.assisteddarwinism.ultimatesmashtracker.player.repository.PlayerRepository
import com.assisteddarwinism.ultimatesmashtracker.playerCharacters.repository.PlayerCharacterCombinationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/players")
class PlayerController {

    @Autowired
    lateinit var tokenValidator: TokenValidator

    @Autowired
    lateinit var playerRepository: PlayerRepository

    @Autowired
    lateinit var matchRepository: MatchRepository

    @Autowired
    lateinit var playerCharacterCombinationRepository: PlayerCharacterCombinationRepository

    @GetMapping
    fun getPlayers(@RequestHeader("X-AuthToken") token: String): List<Player> {
        tokenValidator.checkTokenValid(token)

        return playerRepository.findAll().map { it -> Player(it.id!!, it.name) }.filter { !it.isDeleted }
    }

    @GetMapping("/{playerId}")
    fun getPlayerDetails(@RequestHeader("X-AuthToken") token: String, @PathVariable("playerId") playerId: Long): Player {
        tokenValidator.checkTokenValid(token)
        if (!playerRepository.existsById(playerId) || playerRepository.findById(playerId).get().isDeleted) throw ResourceNotFoundException()
        val player = playerRepository.findById(playerId).get()
        val matches = matchRepository.findAll()
                .map {
                    Match(it, playerCharacterCombinationRepository.findAllByMatchId(it.id!!)
                            .map { PlayerCharacterRelation(it) })
                }

        return if (matches.isNotEmpty()) {
            val wins = matches.count { it.winner == player.id }
            val losses = wins - matches.size
            val mostPlayedCharId = matches.flatMap { it.players }
                    .filter { it.playerId == player.id }
                    .groupBy { it.characterId }
                    .maxBy { it.value.size }!!.key
            val mostRecentMatchId = matches.maxBy { it.time }!!.id
            Player(player.id!!, player.name, wins, losses, mostPlayedCharId, mostRecentMatchId)
        } else {
            Player(player.id!!, player.name)
        }
    }

    @GetMapping("/me")
    fun getMe(@RequestHeader("X-AuthToken") token: String): Player {
        return getPlayerDetails(token, tokenValidator.getIdFromToken(token))
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