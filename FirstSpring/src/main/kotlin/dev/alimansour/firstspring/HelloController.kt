package dev.alimansour.firstspring

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/hello")
    fun hello(): HelloResponse =
        HelloResponse(message = "Hello, World!")

    @GetMapping("/hello/{name}")
    fun helloParam(@PathVariable name: String): HelloResponse =
        HelloResponse(message = "Hello, $name!")

    @PostMapping("/hello")
    fun helloPost(@RequestBody name: String): HelloResponse =
        HelloResponse(message = "Hello, $name!")
}
