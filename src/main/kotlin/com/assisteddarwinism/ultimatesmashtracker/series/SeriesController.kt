package com.assisteddarwinism.ultimatesmashtracker.series

import com.assisteddarwinism.ultimatesmashtracker.auth.TokenValidator
import com.assisteddarwinism.ultimatesmashtracker.model.exception.ResourceNotFoundException
import com.assisteddarwinism.ultimatesmashtracker.series.model.Series
import com.assisteddarwinism.ultimatesmashtracker.series.repository.SeriesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/series")
class SeriesController {

    @Autowired
    lateinit var tokenValidator: TokenValidator

    @Autowired
    lateinit var seriesRepository: SeriesRepository

    @GetMapping
    fun getSeries(@RequestHeader("X-AuthToken") token: String): List<Series> {
        tokenValidator.checkTokenValid(token)

        return seriesRepository.findAll().toList()
    }

    @PostMapping
    fun addSeries(@RequestHeader("X-AuthToken") token: String, @RequestBody series: Series) {
        tokenValidator.checkTokenAdmin(token)
        seriesRepository.save(series)
    }

    @DeleteMapping("/{seriesId}")
    fun removeSeries(@RequestHeader("X-AuthToken") token: String, @PathVariable seriesId: Long) {
        tokenValidator.checkTokenAdmin(token)
        if (!seriesRepository.existsById(seriesId)) throw ResourceNotFoundException()
        seriesRepository.deleteById(seriesId)
    }

}