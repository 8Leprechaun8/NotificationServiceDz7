package org.example.kafka.consumer;

import org.example.kafka.dto.UserMessage;
import org.example.service.impl.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@KafkaListener(id = "users-group", topics = "users")
public class UserMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserMessageHandler.class);

    private final EmailServiceImpl emailService;

    @Autowired
    public UserMessageHandler(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @KafkaHandler
    @Transactional
    public void handleUserMessage(UserMessage userMessage) {
        logger.info("Получен пользователь: " + userMessage.getEmail());
        switch (userMessage.getOperationType()) {
            case CREATE:
                emailService.sendSimpleMessage(
                        userMessage.getEmail(),
                        "Добавление пользователя",
                        "Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
                break;
            case DELETE:
                emailService.sendSimpleMessage(
                        userMessage.getEmail(),
                        "Удаление пользователя",
                        "Здравствуйте! Ваш аккаунт был удалён.");
                break;
            default:
                break;
        }
    }

    @KafkaHandler(isDefault = true)
    public void handleUnknown(Object unknown) {
        logger.error("Получен неизвестный тип сообщения: " + unknown.getClass().getName());
    }
}
