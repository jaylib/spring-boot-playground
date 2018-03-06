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

@RestController
class Controller {

    @Autowired
    lateinit var cardService: CardService

//    @GetMapping("/{id}")
//    fun getName(@PathVariable("id") id: Int): String {
//        return "${cardService.findCard(id)}\n"
//    }

    @PostMapping("/cards")
    fun createCard(@RequestBody card: NewCard): Mono<ServerResponse>? {
        cardService.createCard(card)
        return ServerResponse.created(URI.create("test")).build()
    }

    @GetMapping("/cards/{id}")
    fun getCard(@PathVariable("id") id: UUID) = cardService.getId(id)
}

data class NewCard(val title: String, val author: String, val greetingText: String)
