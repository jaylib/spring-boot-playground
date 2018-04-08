package com.breuninger.archplayground.service

import com.breuninger.archplayground.Broadcaster
import com.breuninger.archplayground.model.Card
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.*
import javax.annotation.PostConstruct

@Service
class CardService(private val cardRepository: CardRepository, private val broadCaster: Broadcaster) {

    fun createCard(card: NewCard): Mono<Card>{
        val realCard = Card(card.id, card.title, card.greetingText, card.author, Instant.now(), Instant.now())
        broadCaster.broadcast("card", realCard)
        return cardRepository.save(realCard)
    }

    fun getId(id: UUID) = cardRepository.findById(id)
    fun findAll() = cardRepository.findAll()
    fun findTailable() = cardRepository.findWithTailableCursorBy()

    @PostConstruct
    fun truncate() = cardRepository.deleteAll().subscribe()
}

data class NewCardAutoId(val title: String, val author: String, val greetingText: String)
data class NewCard(val id: UUID, val title: String, val author: String, val greetingText: String)