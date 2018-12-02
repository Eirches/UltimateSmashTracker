package com.assisteddarwinism.ultimatesmashtracker.auth.repository.password

import org.springframework.data.repository.CrudRepository

interface PasswordRepository : CrudRepository<PasswordDTO, Long>