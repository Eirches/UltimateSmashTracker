package com.assisteddarwinism.ultimatesmashtracker.playerCharacters.repository

import com.assisteddarwinism.ultimatesmashtracker.playerCharacters.model.PlayerCharacterCombination
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayerCharacterCombinationRepository : CrudRepository<PlayerCharacterCombination, Long> {
    fun findAllByMatchId(matchId: Long) : Collection<PlayerCharacterCombination>
}