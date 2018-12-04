package com.assisteddarwinism.ultimatesmashtracker.stage.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="Stages")
data class Stage(val name: String, val series: Long, @Id @GeneratedValue val id: Long?)