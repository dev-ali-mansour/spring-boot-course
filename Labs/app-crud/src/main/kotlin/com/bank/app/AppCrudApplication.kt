package com.bank.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AppCrudApplication

fun main(args: Array<String>) {
    runApplication<AppCrudApplication>(*args)
}
