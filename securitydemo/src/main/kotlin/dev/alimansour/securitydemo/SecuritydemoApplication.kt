package dev.alimansour.securitydemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SecuritydemoApplication

fun main(args: Array<String>) {
    runApplication<SecuritydemoApplication>(*args)
}
