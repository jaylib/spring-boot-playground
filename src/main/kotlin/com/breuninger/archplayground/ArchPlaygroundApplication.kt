package com.breuninger.archplayground

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ArchPlaygroundApplication

fun main(args: Array<String>) {
    runApplication<ArchPlaygroundApplication>(*args)
}
