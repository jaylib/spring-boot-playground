package com.breuninger.archplayground

import com.breuninger.archplayground.model.Card
import com.breuninger.archplayground.service.CardRepository
import com.breuninger.archplayground.service.CardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
open class DemoService(private val cardService: CardService) {

    val webClient: WebClient = WebClient.create("http://localhost:8089/cards")


    @Scheduled(fixedRate = 1000)
    fun newYear() {

        webClient.get().retrieve().bodyToFlux(Card::class.java).subscribe { it
            println(it)
//            cardService.createCard(NewCard(it.title, it.author, it.greetingText)).subscribe()
        }

    }
}