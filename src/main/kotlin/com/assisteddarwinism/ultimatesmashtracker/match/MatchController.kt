package com.assisteddarwinism.ultimatesmashtracker.match

import com.assisteddarwinism.ultimatesmashtracker.auth.TokenValidator
import com.assisteddarwinism.ultimatesmashtracker.match.model.Match
import com.assisteddarwinism.ultimatesmashtracker.match.repository.MatchRepository
import com.fasterxml.jackson.databind.deser.DataFormatReaders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/matches")
class MatchController {

    @Autowired
    lateinit var tokenValidator: TokenValidator

    @Autowired
    lateinit var matchRepository: MatchRepository

    @Autowired
    lateinit var matchTransformer: MatchTransformer

    @GetMapping
    fun getMatches(@RequestHeader("X-AuthToken") token: String) : List<Match> {
        tokenValidator.checkTokenValid(token)

        return matchRepository.findAll().map { it -> matchTransformer.matchFromDTO(it) }
    }

    @PostMapping
    fun addMatch(@RequestHeader("X-AuthToken") token: String, @RequestBody match : Match) {
        tokenValidator.checkTokenValid(token)

        matchRepository.save(matchTransformer.matchToDTO(match))
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
    fun updateMatch(@RequestHeader("X-AuthToken") token: String, @RequestBody match:Match, @PathVariable("matchId") matchId: Long) {
       tokenValidator.checkTokenValid(token)
    }

}