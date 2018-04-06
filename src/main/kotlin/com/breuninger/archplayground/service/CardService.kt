package com.breuninger.archplayground.service

import com.breuninger.archplayground.CardHandler
import com.breuninger.archplayground.model.Card
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.time.Instant
import java.util.*
import javax.annotation.PostConstruct

@Service
class CardService(private val cardRepository: CardRepository, private val cardHandler: CardHandler) {

    fun createCard(card: NewCard): Mono<Card>{
        val realCard = Card(card.id, card.title, card.greetingText, card.author, Instant.now(), Instant.now())
        cardHandler.broadcast(realCard)
        return cardRepository.save(realCard)
    }

    fun getId(id: UUID) = cardRepository.findById(id)
    fun findAll() = cardRepository.findAll()

    @PostConstruct
    fun truncate() = cardRepository.deleteAll().subscribe()
}

data class NewCard(val id: UUID, val title: String, val author: String, val greetingText: String)