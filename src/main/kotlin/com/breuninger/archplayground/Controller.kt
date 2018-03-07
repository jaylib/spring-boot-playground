package com.breuninger.archplayground

import com.breuninger.archplayground.model.Card
import com.breuninger.archplayground.service.CardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.URI
import java.util.*
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@RestController
class Controller {

    @Autowired
    lateinit var cardService: CardService

    @PostMapping("/cards")
    fun createCard(@RequestBody card: NewCard): Mono<Card>? {
        return cardService.createCard(card)
    }

    @GetMapping("/cards/{id}")
    fun getCard(@PathVariable("id") id: UUID) = cardService.getId(id)

    @GetMapping("/cards")
    fun getCards() = cardService.findAll()
}

data class NewCard(val title: String, val author: String, val greetingText: String)