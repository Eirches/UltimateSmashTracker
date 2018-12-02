package com.assisteddarwinism.ultimatesmashtracker.character

import com.assisteddarwinism.ultimatesmashtracker.auth.TokenValidator
import com.assisteddarwinism.ultimatesmashtracker.character.repository.CharacterRepository
import com.assisteddarwinism.ultimatesmashtracker.character.model.Character
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/characters")
class CharacterController {

    @Autowired
    lateinit var tokenValidator: TokenValidator

    @Autowired
    lateinit var characterRepository : CharacterRepository

    @GetMapping
    fun getCharacters(@RequestHeader("X-AuthToken") token: String) : List<Character>{
        tokenValidator.checkTokenValid(token)
        return characterRepository.findAll().toList()
    }

    @GetMapping("/{characterId}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    fun getCharacterDetails(@RequestHeader("X-Authtoken") token: String, @PathVariable characterId: Long) {
        tokenValidator.checkTokenValid(token)
    }

    @PostMapping
    fun addCharacter(@RequestHeader("X-AuthToken") token: String, @RequestBody character: Character) {
        tokenValidator.checkTokenAdmin(token)
        characterRepository.save(character)
    }

    @DeleteMapping("/{characterId}")
    fun removeCharacter(@RequestHeader("X-AuthToken") token: String, @PathVariable characterId: Long) {
        tokenValidator.checkTokenAdmin(token)
        characterRepository.deleteById(characterId)
    }

    @GetMapping("/{characterId}/matches")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    fun getCharacterMatches(@RequestHeader("X-Authtoken") token: String, @PathVariable characterId: Long) {
        tokenValidator.checkTokenValid(token)
    }

}
