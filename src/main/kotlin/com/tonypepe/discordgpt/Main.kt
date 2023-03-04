package com.tonypepe.discordgpt

import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.MessageType
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent

val appChatBot = AppChatBot(AppConfig.openAIApiKey(), AppConfig.botName(), AppConfig.botLanguage())

fun main() {
    val jda = JDABuilder.createDefault(
        AppConfig.discordToken(),
        GatewayIntent.MESSAGE_CONTENT,
        GatewayIntent.GUILD_MESSAGES,
        GatewayIntent.GUILD_MEMBERS,
    )
        .addEventListeners(object : ListenerAdapter() {
            override fun onMessageReceived(event: MessageReceivedEvent) {
                // check if message is in whitelist channel
                if (!isInWhitelistChannel(event)) {
                    return
                }
                // create a thread if the message is not from a thread
                if (!event.isFromThread) {
                    event.message.createThreadChannel(event.message.contentDisplay)
                        .setAutoArchiveDuration(ThreadChannel.AutoArchiveDuration.TIME_1_HOUR)
                        .queue { thread ->
                            val messages =
                                listOf(ChatMessage(ChatMessageRole.USER.value(), event.message.contentDisplay))
                            appChatBot.askChatGpt(messages).firstOrNull()?.let {
                                thread.sendMessage(it.message.content).queue()
                            }
                        }
                    return
                }
                // ----- below only for thread message -----
                if (event.message.type == MessageType.DEFAULT && !event.message.author.isBot) {
                    val messageHistory = mutableListOf<ChatMessage>()
                    // add first message
                    val firstMessage = event.channel.asThreadChannel().retrieveParentMessage().complete()
                    messageHistory.add(ChatMessage(ChatMessageRole.USER.value(), firstMessage.contentDisplay))
                    // add message history in thread
                    event.channel.iterableHistory.reverse().forEach { message ->
                        if (message.type == MessageType.DEFAULT) {
                            when (message.author.isBot) {
                                true -> messageHistory.add(
                                    ChatMessage(
                                        ChatMessageRole.ASSISTANT.value(),
                                        message.contentDisplay
                                    )
                                )

                                false -> messageHistory.add(
                                    ChatMessage(
                                        ChatMessageRole.USER.value(),
                                        message.contentDisplay
                                    )
                                )
                            }
                        }
                    }
                    // ask gpt
                    try {
                        appChatBot.askChatGpt(messageHistory).firstOrNull()?.let {
                            event.channel.sendMessage(it.message.content).queue()
                        }
                    } catch (e: Exception) {
                        event.channel.sendMessage("QQ, something went wrong").queue()
                        e.printStackTrace()
                    }
                    return
                }
            }
        })
        .build()
}

fun isInWhitelistChannel(event: MessageReceivedEvent): Boolean {
    var channelId = event.channel.id
    // if message is from thread, get parent channel id
    if (event.isFromThread) {

        channelId = event.channel.asThreadChannel().parentChannel.id
    }
    // check if message is in whitelist channel
    AppConfig.discordChannelWhitelist().forEach {
        if (channelId == it) {
            return true
        }
    }
    return false
}
