# Discord GPT BOT

在 Discord 中和 chatGPT 聊天。

## 特色

- 使用 chatGPT 模型 "gpt-3.5-turbo"
- 用 Docker 部署
- 可以根據歷史記錄聊天
- 使用討論串回覆，避免要請除歷史紀錄的麻煩
- Channel 白名單，只有在白名單中的 Channel 才會回覆

## 部署

前置作業

- 安裝 Docker
- 安裝 Docker Compose
- 申請 [OpenAi](https://platform.openai.com/) 的 API Key
- 申請 [Discord Bot](https://discord.com/developers/applications)，並取得 Bot Token

### Docker Compose

Example `docker-compose.yml`:

```yml
version: "3.8"

services:
  app:
    image: tonypepe/discordgpt:latest
    environment:
      BOT_LANGUAGE: "繁體中文"
      BOT_NAME: "myGPT"
      DISCORD_CHANNEL_WHITELIST: "1234567890,0987654321"
      DISCORD_TOKEN: "myDiscordBotToken"
      OPENAI_API_KEY: "myOpenAiApiKey"

```

### 環境變數

- `DISCORD_CHANNEL_WHITELIST`: (必填) Channel 白名單，預設不回覆任何 Channel。多個 Channel ID 以 `,` 分隔，中間不要有空格。
- `DISCORD_TOKEN`: (必填)Discord Bot Token
- `OPENAI_API_KEY`: (必填) OpenAi API Key
- `BOT_LANGUAGE`：語言，預設為 `English`
- `BOT_NAME`：Bot 名稱，預設為 `chatGPT`
