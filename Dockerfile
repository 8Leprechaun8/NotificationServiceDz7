# --- ЭТАП 1: Сборка приложения ---
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Копируем файлы проекта
COPY pom.xml .
COPY src ./src

# Собираем приложение
RUN mvn clean package -DskipTests

# --- ЭТАП 2: Запуск ---
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Копируем собранный JAR из первого этапа
COPY --from=builder /app/target/NotificationServiceDz7-1.0-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]