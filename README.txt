Чтобы опробовать функционал приложения, нужно:

1) Зайти в UserServiceDz7 и поднять контейнеры. Команда: docker-compose up -d
2) Запустить приложение SpringBoot для ConfigurationServerDz7. Это конфигурации для всех сервисов.
3) Запустить приложение SpringBoot для EurekaServerDz7. Это сервер обнаружения служб.
4) Запустить приложение SpringBoot для GatewayServerDz7. Это шлюз.
5) Запустить приложение SpringBoot для UserServiceDz7.
6) Запустить приложение SpringBoot для NotificationServiceDz7.
7) Чтобы опробовать функционал паттерна шлюза, нужно перейти по любой из ссылок:
http://localhost:8072/user-service/api/circuit-breaker-test
http://localhost:8072/notification-service/api/email/test-for-circuit-breaker
Нажимать нужно много раз, т.к. имитируется иногда неработоспособность сервиса
NotificationServiceDz7.
8) В пункте 7 одновременно проверяется работоспособность circuit breaker.
Только много раз нужно обновлять страницу.

---
Примечание:
---
Для работы с пользователями нужно логиниться. Только не через шлюз работа идет.
Это страница: http://localhost:8080/login
Логин: mokrushin
Пароль: 123
---