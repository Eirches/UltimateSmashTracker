package com.assisteddarwinism.ultimatesmashtracker.match.model

import java.util.*

data class Match(var players: MutableList<PlayerCharacterRelation>, val winner: Long, val stage: Long, val items: Boolean, val recorder: Long, val time: Date = Date(), val id: Long?)
