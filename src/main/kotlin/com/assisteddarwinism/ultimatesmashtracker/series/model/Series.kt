package com.assisteddarwinism.ultimatesmashtracker.series.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="Series")
data class Series(val name: String, @Id @GeneratedValue val id: Long?)