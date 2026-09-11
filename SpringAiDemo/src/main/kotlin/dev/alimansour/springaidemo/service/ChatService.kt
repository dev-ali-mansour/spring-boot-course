package dev.alimansour.springaidemo.service

import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.google.genai.GoogleGenAiChatOptions
import org.springframework.stereotype.Service

@Service
class ChatService(private val chatModel: ChatModel) {
    fun getResponse(prompt: String): String? {
        val response = chatModel.call(prompt)
        return response
    }

    fun getResponseOptions(prompt: String): String? {
        val response = chatModel.call(
            Prompt(
                prompt,
                GoogleGenAiChatOptions.builder()
                    .model("gemini-3.8-flash")
                    .temperature(0.4)
                    .build()
            )
        )
        return response.result?.output?.text
    }


}