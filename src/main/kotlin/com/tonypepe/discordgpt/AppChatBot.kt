package com.tonypepe.discordgpt

import com.theokanning.openai.completion.chat.ChatCompletionChoice
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import com.theokanning.openai.service.OpenAiService
import java.time.Duration

class AppChatBot(openaiToken: String, val name: String = "chatGPT", val language: String = "English") {
    private val service = OpenAiService(openaiToken, Duration.ofSeconds(30))

    fun askChatGpt(messages: List<ChatMessage>): List<ChatCompletionChoice> {
        // add system message
        val chatMsg = mutableListOf(
            ChatMessage(ChatMessageRole.SYSTEM.value(), "You are a helpful assistant named $name."),
            ChatMessage(ChatMessageRole.SYSTEM.value(), "By default, you are in $language, but it can be changed."),
        ).apply { addAll(messages) }
        // make request
        val chatRequest = ChatCompletionRequest.builder()
            .model("gpt-3.5-turbo")
            .messages(chatMsg)
            .build()
        // ask gpt
        return service.createChatCompletion(chatRequest).choices
    }
}
