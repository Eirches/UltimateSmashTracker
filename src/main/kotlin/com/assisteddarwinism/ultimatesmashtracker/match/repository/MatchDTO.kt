package com.assisteddarwinism.ultimatesmashtracker.match.repository

import com.assisteddarwinism.ultimatesmashtracker.match.model.Match
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="Matches")
data class MatchDTO(val winner: Long, val recorder: Long, val stage: Long, val items: Boolean, val time: Date, @Id @GeneratedValue val id: Long?) {
    constructor(match: Match) : this(match.winner, match.recorder ?: 0, match.stage, match.items, match.time, match.id)
}