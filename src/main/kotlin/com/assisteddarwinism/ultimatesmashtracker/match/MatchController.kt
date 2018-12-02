package com.assisteddarwinism.ultimatesmashtracker.match

import com.assisteddarwinism.ultimatesmashtracker.auth.TokenValidator
import com.assisteddarwinism.ultimatesmashtracker.match.model.Match
import com.assisteddarwinism.ultimatesmashtracker.match.repository.MatchRepository
import com.fasterxml.jackson.databind.deser.DataFormatReaders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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


}