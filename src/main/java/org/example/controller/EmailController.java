package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.dto.EmailMessage;
import org.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Operation(summary = "Отправка почтового сообщения")
    @PostMapping("/send")
    public void sendMessage(@RequestBody EmailMessage message) {
        emailService.sendSimpleMessage(message.getTo(), message.getSubject(), message.getText());
    }

    @Operation(summary = "Метод для демонстрации работы Circuit Breaker")
    @GetMapping("/test-for-circuit-breaker")
    public String testForCircuitBreaker() throws TimeoutException {
        Random random = new Random();
        int randomNum = random.nextInt(4) + 1;
        if (randomNum > 2) {
            try {
                Thread.sleep(5000);
                throw new TimeoutException();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return "Метод сработал";
    }
}
