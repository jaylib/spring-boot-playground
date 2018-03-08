package com.breuninger.archplayground.service

import com.breuninger.archplayground.model.Comment
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import java.util.*

interface CommentRepository : ReactiveSortingRepository<Comment, UUID>
