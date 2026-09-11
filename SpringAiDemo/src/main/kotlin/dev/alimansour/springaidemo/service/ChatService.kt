package dev.alimansour.springaidemo.service

import org.springframework.ai.chat.model.ChatModel
import org.springframework.stereotype.Service

@Service
class ChatService(private val chatModel: ChatModel) {
    fun getResponse(prompt: String): String? {
        val response = chatModel.call(prompt)
        return response
    }
}