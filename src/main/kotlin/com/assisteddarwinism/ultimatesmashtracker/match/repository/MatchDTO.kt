package com.assisteddarwinism.ultimatesmashtracker.match.repository

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="Matches")
data class MatchDTO(
        val playerOne: Long,
        val characterOne: Long,
        val playerTwo: Long,
        val characterTwo: Long,
        val winner: Long,
        val recorder: Long,
        val stage: Long,
        val items: Boolean,
        val time: Date,
        val playerThree: Long?,
        val characterThree: Long?,
        val playerFour: Long?,
        val characterFour: Long?,
        val playerFive: Long?,
        val characterFive: Long?,
        val playerSix: Long?,
        val characterSix: Long?,
        val playerSeven: Long?,
        val characterSeven: Long?,
        val playerEight: Long?,
        val characterEight: Long?,
       @Id @GeneratedValue val id: Long?
)