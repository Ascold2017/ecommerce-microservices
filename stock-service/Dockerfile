# Этап 1: Сборка приложения
FROM eclipse-temurin:25-jdk-alpine AS builder
WORKDIR /app

# Устанавливаем мавен
RUN apk add --no-cache maven

# Копируем pom.xml и кешируем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем исходники и собираем jar
COPY src ./src
RUN mvn clean package -DskipTests

# Этап 2: Финальный легковесный образ для запуска
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Копируем собранный jar-файл
COPY --from=builder /app/target/*.jar app.jar

ENV JAVA_OPTS="-XX:+UseG1GC -XX:+ExitOnOutOfMemoryError"

EXPOSE 8084

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]