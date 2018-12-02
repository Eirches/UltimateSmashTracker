package com.assisteddarwinism.ultimatesmashtracker.match.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository : CrudRepository<MatchDTO, Long>