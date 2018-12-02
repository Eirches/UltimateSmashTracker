package com.assisteddarwinism.ultimatesmashtracker.player.repository

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Players")
data class PlayerDTO(
        val name: String,
        val admin: Boolean = false,
        val isDeleted: Boolean = false,
        @Id @GeneratedValue val id: Long? = null
)
