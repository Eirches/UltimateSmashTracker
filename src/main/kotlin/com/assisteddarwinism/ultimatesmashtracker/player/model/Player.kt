package com.assisteddarwinism.ultimatesmashtracker.player.model

data class Player(val id: Long, val name: String, val wins: Int? = null, val losses: Int? = null, val mostPlayedCharacter: Long? = null, val lastMatch: Long? = null, val isDeleted: Boolean = false)
