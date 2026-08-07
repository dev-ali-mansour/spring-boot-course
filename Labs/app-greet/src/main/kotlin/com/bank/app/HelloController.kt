package com.bank.app

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/hello")
class HelloController {
    @GetMapping
    fun sayHello(): String {
        return "Welcome to Hello Banking API!"
    }

    @PostMapping
    fun greetUser(@RequestBody name: String): String {
        return "Hello, $name! Welcome to Hello Banking API!"
    }
}
