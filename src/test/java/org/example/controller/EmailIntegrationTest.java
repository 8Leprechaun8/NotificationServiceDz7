package org.example.controller;

import io.restassured.RestAssured;
import org.example.service.EmailService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailIntegrationTest {

    static GenericContainer<?> mailpit = new GenericContainer<>("axllent/mailpit:latest")
            .withExposedPorts(1025, 8025)
            .waitingFor(Wait.forHttp("/").forPort(8025));

    @BeforeAll
    static void beforeAll() {
        mailpit.start();
    }

    @AfterAll
    static void afterAll() {
        mailpit.stop();
    }

    @DynamicPropertySource
    static void configureMailProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.mail.host", mailpit::getHost);
        registry.add("spring.mail.port", () -> mailpit.getMappedPort(1025));
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://" + mailpit.getHost() + ":" + mailpit.getMappedPort(8025);

        // Очищаем почтовый ящик перед каждым тестом
        given().delete("/api/v1/messages");
    }

    @Autowired
    private EmailService emailService;

    @Test
    void shouldSendEmailSuccessfullyTest() {

        emailService.sendSimpleMessage("to@example.com", "Тема", "Привет, мир!");

        given()
                .get("/api/v1/messages")
                .then()
                .statusCode(200)
                .body("messages[0].Subject", equalTo("Тема"))
                .body("messages[0].Snippet", equalTo("Привет, мир!"))
                .body("messages[0].To[0].Address", equalTo("to@example.com"));
    }
}
