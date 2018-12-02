package com.assisteddarwinism.ultimatesmashtracker.stage.repository

import com.assisteddarwinism.ultimatesmashtracker.stage.model.Stage
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface StageRepository : CrudRepository<Stage, Long>