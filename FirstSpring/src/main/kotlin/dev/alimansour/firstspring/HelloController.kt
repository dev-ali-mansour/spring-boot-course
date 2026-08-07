package dev.alimansour.firstspring

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/hello")
    fun hello() = "Hello World!"

    @PostMapping("/hello")
    fun helloPost(@RequestBody name: String) = "Hello $name!"
}
