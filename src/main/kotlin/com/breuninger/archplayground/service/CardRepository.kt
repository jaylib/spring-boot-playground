package com.breuninger.archplayground.service

import com.breuninger.archplayground.model.Card
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import java.util.*
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable
import reactor.core.publisher.Flux

interface CardRepository : ReactiveMongoRepository<Card, UUID> {

    @Tailable
    fun findWithTailableCursorBy(): Flux<Card>

}

