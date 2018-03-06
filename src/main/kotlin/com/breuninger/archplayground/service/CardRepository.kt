package com.breuninger.archplayground.service

import com.breuninger.archplayground.model.Card
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import java.util.*

interface CardRepository : ReactiveSortingRepository<Card, UUID>
