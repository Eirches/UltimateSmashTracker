package com.assisteddarwinism.ultimatesmashtracker.stage

import com.assisteddarwinism.ultimatesmashtracker.auth.TokenValidator
import com.assisteddarwinism.ultimatesmashtracker.stage.model.Stage
import com.assisteddarwinism.ultimatesmashtracker.stage.repository.StageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stages")
class StageController {

    @Autowired
    lateinit var tokenValidator: TokenValidator

    @Autowired
    lateinit var stageRepository: StageRepository

    @GetMapping
    fun getStages(@RequestHeader("X-AuthToken") token: String): List<Stage> {
        tokenValidator.checkTokenValid(token)

        return stageRepository.findAll().toList()
    }

    @PostMapping
    fun addStage(@RequestHeader("X-AuthToken") token: String, @RequestBody stage: Stage) {
        tokenValidator.checkTokenAdmin(token)
        stageRepository.save(stage)
    }

    @DeleteMapping("/{stageId}")
    fun removeStage(@RequestHeader("X-AuthToken") token: String, @PathVariable("stageId") stageId: Long) {
        tokenValidator.checkTokenAdmin(token)
        stageRepository.deleteById(stageId)
    }

    @GetMapping("/{stageId}/matches")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    fun getStageMatches(@RequestHeader("X-AuthToken") token: String, @PathVariable("stageId") stageId: Long) {
        tokenValidator.checkTokenValid(token)
    }

}