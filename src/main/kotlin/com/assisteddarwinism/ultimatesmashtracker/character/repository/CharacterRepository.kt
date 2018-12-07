package com.assisteddarwinism.ultimatesmashtracker.character.repository

import com.assisteddarwinism.ultimatesmashtracker.character.model.Character
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CharacterRepository : CrudRepository<Character, String>