package com.tonypepe.discordgpt

// TODO config file
class AppConfig {
    companion object {
        fun discordToken(): String {
            if (System.getenv("DISCORD_TOKEN").isNullOrBlank()) {
                throw Exception("DISCORD_TOKEN is not set")
            }
            return System.getenv("DISCORD_TOKEN")
        }

        fun openAIApiKey(): String {
            if (System.getenv("OPENAI_API_KEY").isNullOrBlank()) {
                throw Exception("OPENAI_API_KEY is not set")
            }
            return System.getenv("OPENAI_API_KEY")
        }

        fun discordChannelWhitelist(): List<String> {
            if (System.getenv("DISCORD_CHANNEL_WHITELIST").isNullOrBlank()) {
                throw Exception("DISCORD_CHANNEL_WHITELIST is not set")
            }
            System.getenv("DISCORD_CHANNEL_WHITELIST")?.let {
                return it.split(",")
            }
            return emptyList()
        }

        fun botName(): String {
            return System.getenv("BOT_NAME").let { name ->
                if (name.isNullOrBlank()) "chatGPT" else name
            }
        }

        fun botLanguage(): String {
            return System.getenv("BOT_LANGUAGE").let { language ->
                if (language.isNullOrBlank()) "English" else language
            }
        }
    }
}