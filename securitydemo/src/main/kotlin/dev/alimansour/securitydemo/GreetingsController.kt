package dev.alimansour.securitydemo

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingsController {
    @GetMapping("/hello")
    fun sayHello(): String {
        return "Hello"
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    fun userEndpoint(): String {
        return "Hello, User!"
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    fun adminEndpoint(): String {
        return "Hello, Admin!"
    }
}
