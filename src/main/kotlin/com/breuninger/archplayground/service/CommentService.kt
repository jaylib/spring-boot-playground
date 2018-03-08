package com.breuninger.archplayground.service

import com.breuninger.archplayground.model.Card
import com.breuninger.archplayground.model.Comment
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.*


@Service
class CommentService(private val commentRepository: CommentRepository) {

    fun createComment(comment: NewComment, cardId: UUID): Mono<Comment> {
        val realComment = Comment(UUID.randomUUID(), cardId, comment.text, comment.author, Instant.now(), Instant.now())
        return commentRepository.save(realComment)
    }

    fun getId(id: UUID) = commentRepository.findById(id)
    fun findAll(cardId: UUID) = commentRepository.findAll().filter { it.cardId == cardId }
}

data class NewComment(val text: String, val author: String)