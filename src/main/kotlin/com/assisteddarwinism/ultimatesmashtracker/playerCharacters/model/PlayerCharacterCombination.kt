package com.assisteddarwinism.ultimatesmashtracker.playerCharacters.model

import com.assisteddarwinism.ultimatesmashtracker.match.model.PlayerCharacterRelation
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="PlayerCharacters")
data class PlayerCharacterCombination(val playerId: Long, val characterId: Long, val matchId: Long, @Id @GeneratedValue val id: Long?) {
    constructor(matchId: Long, player: PlayerCharacterRelation) : this(player.playerId, player.characterId, matchId, null)
}

