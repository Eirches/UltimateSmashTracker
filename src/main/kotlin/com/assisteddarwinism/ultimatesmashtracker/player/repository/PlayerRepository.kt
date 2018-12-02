package com.assisteddarwinism.ultimatesmashtracker.player.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayerRepository : CrudRepository<PlayerDTO, Long> {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): PlayerDTO
}
