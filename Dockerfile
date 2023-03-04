FROM azul/zulu-openjdk:17-latest as builder

COPY . /app
WORKDIR /app
RUN ./gradlew installDist

FROM azul/zulu-openjdk:17-latest

COPY --from=builder /app/build/install/discordgpt /app
WORKDIR /app
CMD ["bin/discordgpt"]
