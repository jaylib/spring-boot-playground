package com.breuninger.archplayground

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
@EnableReactiveMongoRepositories
open class ArchPlaygroundApplication

fun main(args: Array<String>) {
    runApplication<ArchPlaygroundApplication>(*args)
}
