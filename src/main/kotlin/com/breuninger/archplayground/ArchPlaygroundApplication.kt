package com.breuninger.archplayground

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
open class ArchPlaygroundApplication

fun main(args: Array<String>) {
    runApplication<ArchPlaygroundApplication>(*args)
}
