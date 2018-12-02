package com.assisteddarwinism.ultimatesmashtracker.match

import com.assisteddarwinism.ultimatesmashtracker.match.model.Match
import com.assisteddarwinism.ultimatesmashtracker.match.model.PlayerCharacterRelation
import com.assisteddarwinism.ultimatesmashtracker.match.repository.MatchDTO
import com.assisteddarwinism.ultimatesmashtracker.model.exception.InvalidInputException
import org.springframework.stereotype.Component

@Component
class MatchTransformer {

    fun matchToDTO(match: Match) : MatchDTO {

        var playerOne: Long = 0
        var characterOne: Long = 0
        var playerTwo: Long = 0
        var characterTwo: Long = 0
        var playerThree: Long? = null
        var characterThree: Long? = null
        var playerFour:  Long? = null
        var characterFour: Long? = null
        var playerFive: Long? = null
        var characterFive: Long? = null
        var playerSix: Long? = null
        var characterSix: Long? = null
        var playerSeven: Long? = null
        var characterSeven: Long? = null
        var playerEight: Long? = null
        var characterEight: Long? = null

        for(i in 1..match.players.count()) {
            when(i) {
                1 -> {
                    playerOne = match.players.get(i).playerId
                    characterOne = match.players.get(i).characterId
                }
                2 -> {
                    playerTwo = match.players.get(i).playerId
                    characterTwo = match.players.get(i).characterId
                }
                3 -> {
                    playerThree = match.players.get(i).playerId
                    characterThree = match.players.get(i).characterId
                }
                4 -> {
                    playerFour = match.players.get(i).playerId
                    characterFour = match.players.get(i).characterId
                }
                5 -> {
                    playerFive = match.players.get(i).playerId
                    characterFive = match.players.get(i).characterId
                }
                6 -> {
                    playerSix = match.players.get(i).playerId
                    characterSix = match.players.get(i).characterId
                }
                7 -> {
                    playerSeven = match.players.get(i).playerId
                    characterSeven = match.players.get(i).characterId
                }
                8 -> {
                    playerEight = match.players.get(i).playerId
                    characterEight = match.players.get(i).characterId
                }
                else -> throw InvalidInputException()
            }
        }

        return MatchDTO(playerOne, characterOne, playerTwo, characterTwo, match.winner, match.recorder, match.stage, match.items, match.time, playerThree, characterThree, playerFour, characterFour, playerFive, characterFive, playerSix, characterSix, playerSeven, characterSeven, playerEight, characterEight, match.id)
    }

    fun matchFromDTO(matchDTO: MatchDTO) : Match {
        var playerCharacterRelations = mutableListOf<PlayerCharacterRelation>()
        playerCharacterRelations.add(PlayerCharacterRelation(matchDTO.playerOne, matchDTO.characterOne))
        playerCharacterRelations.add(PlayerCharacterRelation(matchDTO.playerTwo, matchDTO.characterTwo))
        matchDTO.playerThree?.let { playerCharacterRelations.add(PlayerCharacterRelation(it, matchDTO.characterThree?:0)) }
        matchDTO.playerFour?.let { playerCharacterRelations.add(PlayerCharacterRelation(it, matchDTO.characterFour?:0)) }
        matchDTO.playerFive?.let { playerCharacterRelations.add(PlayerCharacterRelation(it, matchDTO.characterFive?:0)) }
        matchDTO.playerSix?.let { playerCharacterRelations.add(PlayerCharacterRelation(it, matchDTO.characterSix?:0)) }
        matchDTO.playerSeven?.let { playerCharacterRelations.add(PlayerCharacterRelation(it, matchDTO.characterSeven?:0)) }
        matchDTO.playerEight?.let { playerCharacterRelations.add(PlayerCharacterRelation(it, matchDTO.characterEight?:0)) }

        return Match(playerCharacterRelations, matchDTO.winner, matchDTO.stage, matchDTO.items, matchDTO.recorder, matchDTO.time, matchDTO.id)
    }

}