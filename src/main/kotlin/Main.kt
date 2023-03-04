import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import com.theokanning.openai.service.OpenAiService

fun main() {
    val openAiService = OpenAiService(getOpenAIApiKey())
    val chatMessages = listOf(
        ChatMessage(ChatMessageRole.SYSTEM.value(), "You are a helpful assistant, name is \"discordGPT\"."),
        ChatMessage(ChatMessageRole.USER.value(), "Who are you?"),
    )
    val req = ChatCompletionRequest.builder()
        .model("gpt-3.5-turbo")
        .messages(chatMessages)
        .build()
    openAiService.createChatCompletion(req).apply {
        this.choices.forEach {
            println(it.message.content)
        }
        println(this.usage.totalTokens)
    }
}

fun getOpenAIApiKey(): String {
    return System.getenv("OPENAI_API_KEY")
}
