package com.breuninger.archplayground.model

import org.springframework.data.annotation.Id
import java.time.Instant
import java.util.*

data class Card(
        @Id val id: UUID,
        val title: String,
        val greetingText: String,
        val author: String,
        val created: Instant,
        val updated: Instant
)