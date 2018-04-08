package com.breuninger.archplayground.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.*

@Document(collection = "comments")
data class Comment(
        @Id val id: UUID,
        val cardId: UUID,
        val author: String,
        val text: String,
        val created: Instant,
        val updated: Instant
)