package com.breuninger.archplayground

import com.breuninger.archplayground.model.Card
import com.breuninger.archplayground.model.Comment
import com.breuninger.archplayground.service.CardService
import com.breuninger.archplayground.service.CommentService
import com.breuninger.archplayground.service.NewCard
import com.breuninger.archplayground.service.NewComment
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

@CrossOrigin
@RestController
class Controller {

    @Autowired
    lateinit var cardService: CardService

    @Autowired
    lateinit var commentService: CommentService

    @PostMapping("/cards")
    fun createCard(@RequestBody card: NewCard): Mono<Card>? {
        return cardService.createCard(card)
    }

    @GetMapping("/cards/{id}")
    fun getCard(@PathVariable("id") id: UUID) = cardService.getId(id)

    @GetMapping("/cards")
    fun getCards() = cardService.findAll()

    @GetMapping("/cards/{cardId}/comments")
    fun getComments(@PathVariable("cardId") cardId: UUID) = commentService.findAll(cardId)

    @PostMapping("/cards/{cardId}/comments")
    fun createComment(@PathVariable("cardId") cardId: String, @RequestBody comment: NewComment): Mono<Comment>? {
        return commentService.createComment(NewComment(comment.text, comment.author), UUID.fromString(cardId))
    }
}

