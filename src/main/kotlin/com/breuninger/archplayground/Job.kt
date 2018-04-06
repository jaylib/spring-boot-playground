package com.breuninger.archplayground

import com.breuninger.archplayground.model.Card
import com.breuninger.archplayground.service.CardRepository
import com.breuninger.archplayground.service.CardService
import com.breuninger.archplayground.service.NewCard
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
open class JobFetcher(private val cardService: CardService) {

    val webClient: WebClient = WebClient.create("http://10.10.149.220:4242/feed")

    @Scheduled(fixedRate = 7000)
    fun fetchCards() {
        webClient.get().retrieve().bodyToFlux(NewCard::class.java).subscribe { it
            cardService.createCard(NewCard(it.id, it.title, it.author, it.greetingText)).subscribe()
        }
    }
}