package com.assisteddarwinism.ultimatesmashtracker.match

import com.assisteddarwinism.ultimatesmashtracker.auth.TokenValidator
import com.assisteddarwinism.ultimatesmashtracker.match.model.Match
import com.assisteddarwinism.ultimatesmashtracker.match.model.PlayerCharacterRelation
import com.assisteddarwinism.ultimatesmashtracker.match.repository.MatchDTO
import com.assisteddarwinism.ultimatesmashtracker.match.repository.MatchRepository
import com.assisteddarwinism.ultimatesmashtracker.playerCharacters.model.PlayerCharacterCombination
import com.assisteddarwinism.ultimatesmashtracker.playerCharacters.repository.PlayerCharacterCombinationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/matches")
class MatchController {

    @Autowired
    lateinit var tokenValidator: TokenValidator

    @Autowired
    lateinit var matchRepository: MatchRepository

    @Autowired
    lateinit var playerCharacterCombinationRepository: PlayerCharacterCombinationRepository

    @GetMapping
    fun getMatches(@RequestHeader("X-AuthToken") token: String, @RequestParam("playerIds") playerIds: Optional<List<Long>>): List<Match> {
        tokenValidator.checkTokenValid(token)

        var matches = matchRepository.findAll().map { Match(it, playerCharacterCombinationRepository.findAllByMatchId(it.id!!).map { PlayerCharacterRelation(it) }) }
        return if (playerIds.isPresent) {
            matches.filter { it.players.any { it.playerId in playerIds.get() } }
        } else {
            matches
        }
    }

    @PostMapping
    fun addMatch(@RequestHeader("X-AuthToken") token: String, @RequestBody match: Match) {
        tokenValidator.checkTokenValid(token)

        var matchDTO = MatchDTO(match)
        matchRepository.save(matchDTO)
        match.players.forEach { playerCharacterCombinationRepository.save(PlayerCharacterCombination(matchDTO.id!!, it)) }
    }

    @DeleteMapping("/{matchId}")
    fun deleteMatch(@RequestHeader("X-AuthToken") token: String, @PathVariable("matchId") matchId: Long) {
        tokenValidator.checkTokenAdmin(token)
        matchRepository.deleteById(matchId)
    }

    @GetMapping("/{matchId}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    fun getMatchDetails(@RequestHeader("X-AuthToken") token: String, @PathVariable("matchId") matchId: Long) {
        tokenValidator.checkTokenValid(token)
    }

    @PutMapping("/{matchId}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    fun updateMatch(@RequestHeader("X-AuthToken") token: String, @RequestBody match: Match, @PathVariable("matchId") matchId: Long) {
        tokenValidator.checkTokenValid(token)
    }

}