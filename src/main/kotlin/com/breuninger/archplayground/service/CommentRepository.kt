package com.breuninger.archplayground.service

import com.breuninger.archplayground.model.Card
import com.breuninger.archplayground.model.Comment
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.mongodb.repository.Tailable
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Flux
import java.util.*

interface CommentRepository : ReactiveMongoRepository<Comment, UUID> {
    @Tailable
    fun findWithTailableCursorBy(): Flux<Comment>
}
