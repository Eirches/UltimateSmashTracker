package com.assisteddarwinism.ultimatesmashtracker.match.model

import com.assisteddarwinism.ultimatesmashtracker.match.repository.MatchDTO
import java.util.*

data class Match(var players: Collection<PlayerCharacterRelation>, val winner: Long, val stage: Long, val items: Boolean, val recorder: Long?, val time: Date = Date(), val id: Long?) {
    constructor(dto: MatchDTO, players: Collection<PlayerCharacterRelation>) : this(players, dto.winner, dto.stage, dto.items, dto.recorder, dto.time, dto.id)
}


