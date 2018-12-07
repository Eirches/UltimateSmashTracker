package com.assisteddarwinism.ultimatesmashtracker.match.model

import com.assisteddarwinism.ultimatesmashtracker.playerCharacters.model.PlayerCharacterCombination

data class PlayerCharacterRelation(val playerId: Long, val characterId: Long) {
    constructor(dto: PlayerCharacterCombination) : this(dto.playerId, dto.characterId)
}