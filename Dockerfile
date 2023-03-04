FROM azul/zulu-openjdk:17-latest as builder

COPY . /app
WORKDIR /app
RUN ./gradlew build

FROM azul/zulu-openjdk:17-latest

COPY --from=builder /app/build/libs/*.jar /app/discordgpt.jar
WORKDIR /app
CMD ["java", "-jar", "discordgpt.jar"]
