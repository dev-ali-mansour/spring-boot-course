package dev.alimansour.firstspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FirstSpringApplication

fun main(args: Array<String>) {
    runApplication<FirstSpringApplication>(*args)
}
