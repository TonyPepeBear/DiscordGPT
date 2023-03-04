# Discord GPT BOT

Chat with chatGPT on Discord.

[中文 README](README.zh-tw.md)

## Features

- Uses chatGPT model "gpt-3.5-turbo"
- Deployed with Docker
- Can chat based on historical records
- Replies using thread messages to avoid the hassle of removing chat logs
- Channel whitelist - only responds to channels on the whitelist

## Deployment

Prerequisites

- Install Docker
- Install Docker Compose
- Apply for [OpenAI API](https://platform.openai.com/) Key
- Apply for a [Discord Bot](https://discord.com/developers/applications) and obtain Bot Token

### Docker Compose

Example `docker-compose.yml`:

```yml
version: "3.8"

services:
  app:
    image: tonypepe/discordgpt:latest
    restart: unless-stopped
    environment:
      BOT_LANGUAGE: "English"
      BOT_NAME: "myGPT"
      DISCORD_CHANNEL_WHITELIST: "1234567890,0987654321"
      DISCORD_TOKEN: "myDiscordBotToken"
      OPENAI_API_KEY: "myOpenAiApiKey"

```

### Environment variables

- `DISCORD_CHANNEL_WHITELIST`: (required) Channel whitelist; by default, no channel will receive replies. Multiple
  Channel IDs are separated by `,` without spaces.
- `DISCORD_TOKEN`: (required) Discord Bot Token
- `OPENAI_API_KEY`: (required) OpenAi API Key
- `BOT_LANGUAGE`: language; default is `English`
- `BOT_NAME`: Bot name; default is `chatGPT`
