package com.breuninger.archplayground.service

import com.breuninger.archplayground.Broadcaster
import com.breuninger.archplayground.model.Comment
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.*


@Service
class CommentService(private val commentRepository: CommentRepository, private val broadCaster: Broadcaster) {

    fun createComment(comment: NewComment, cardId: UUID): Mono<Comment> {
        val realComment = Comment(UUID.randomUUID(), cardId, comment.text, comment.author, Instant.now(), Instant.now())
        broadCaster.broadcast("comment", realComment)
        return commentRepository.save(realComment)
    }

    fun getId(id: UUID) = commentRepository.findById(id)
    fun findAll(cardId: UUID) = commentRepository.findAll().filter { it.cardId == cardId }
}

data class NewComment(val text: String, val author: String)