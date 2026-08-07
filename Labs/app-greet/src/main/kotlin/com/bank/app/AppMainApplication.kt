package com.bank.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class AppMainApplication

fun main(args: Array<String>) {
    runApplication<AppMainApplication>(*args)
}
