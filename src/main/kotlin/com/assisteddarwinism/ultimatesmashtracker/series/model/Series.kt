package com.assisteddarwinism.ultimatesmashtracker.series.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Series")
data class Series(val name: String, @Id val id: Long)