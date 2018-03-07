package com.breuninger.archplayground.service

import com.breuninger.archplayground.NewCard
import com.breuninger.archplayground.model.Card
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.time.Instant
import java.util.*

@Service
class CardService(private val cardRepository: CardRepository) {

    fun createCard(card: NewCard): Mono<Card>{
        val realCard = Card(UUID.randomUUID(), card.title, card.greetingText, card.author, Instant.now(), Instant.now())
        return cardRepository.save(realCard)
    }

    fun getId(id: UUID) = cardRepository.findById(id)
    fun findAll() = cardRepository.findAll()
}