package dev.alimansour.springaidemo.controller

import dev.alimansour.springaidemo.service.ChatService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GenAIController(private val chatService: ChatService) {
    @GetMapping("/ask-ai")
    fun getResponse(@RequestParam prompt: String): String? {
        return chatService.getResponse(prompt)
    }

    @GetMapping("/ask-ai-options")
    fun getResponseOptions(@RequestParam prompt: String): String? {
        return chatService.getResponseOptions(prompt)
    }
}