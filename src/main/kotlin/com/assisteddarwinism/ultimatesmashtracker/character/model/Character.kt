package com.assisteddarwinism.ultimatesmashtracker.character.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="Characters")
data class Character(val name: String, val series: Long, @Id val id: String)