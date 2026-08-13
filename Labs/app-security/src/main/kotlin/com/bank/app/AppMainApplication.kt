package com.bank.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AppMainApplication

fun main(args: Array<String>) {
    runApplication<AppMainApplication>(*args)
}
