package dev.alimansour.securitydemo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingsController {
    @GetMapping("/hello")
    fun sayHello(): String {
        return "Hello"
    }
}
