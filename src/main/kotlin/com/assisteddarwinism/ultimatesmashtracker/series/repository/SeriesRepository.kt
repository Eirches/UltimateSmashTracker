package com.assisteddarwinism.ultimatesmashtracker.series.repository

import com.assisteddarwinism.ultimatesmashtracker.series.model.Series
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SeriesRepository : CrudRepository<Series, Long>