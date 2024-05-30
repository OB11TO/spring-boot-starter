## Описание проекта Spring-App (Method Execution Time Systems)

Проект Method Execution Time Systems - это система для отслеживания времени выполнения методов в приложении, разработанная с использованием Java и Spring Framework. Он включает в себя следующие компоненты:

1. **Отслеживание времени выполнения методов**: Проект использует Spring AOP (Aspect-Oriented Programming) для отслеживания времени выполнения методов, помеченных аннотациями `@TrackTime` и `@TrackAsyncTime`. Для этого используются аспекты, которые перехватывают вызовы методов и регистрируют время их выполнения.

2. **Асинхронное сохранение данных**: Для асинхронного сохранения данных о времени выполнения методов в базе данных используется сервис, который выполняет эту задачу в фоновом режиме. Это позволяет избежать блокировки основного потока выполнения и повысить производительность приложения.

3. **CRUD операции со студентами**: В проекте также реализован CRUD (Create, Read, Update, Delete) интерфейс для управления данными о студентах. Этот функционал позволяет добавлять, просматривать, обновлять и удалять информацию о студентах в базе данных.

### Пример метода

Ниже приведен пример метода контроллера, который позволяет получить среднее время выполнения всех методов, помеченных аннотациями `@TrackTime` и `@TrackAsyncTime`:

```java
@Operation(summary = "Получить среднее время выполнения всех методов помеченными аннотациями @TrackTime и @TrackAsyncTime")
@GetMapping("/average-execution-time-methods")
public ResponseEntity<List<ExecutionTimeMethodDto>> getStatistics() {
    return ResponseEntity.ok(statisticsService.getAverageExecutionTimeMethods());
}
```
## Запуск проекта Method Execution Time Systems

### Требования

- Docker
- Docker Compose

### Запуск проекта

1. Склонируйте репозиторий на свой компьютер:

    ```bash
    git clone https://github.com/OB11TO/method-execution.git
    ```

2. Перейдите в каталог проекта:

    ```bash
    cd method-execution
    ```

3. Соберите и запустите контейнеры Docker с помощью Docker Compose:

    ```bash
    docker-compose up --build
    ```

### Порты и доступы

- **PostgreSQL**:
    - **Порт:** 5433
    - **База данных:** time_tracking_systems
    - **Пользователь:** migration_user
    - **Пароль:** mgr_user
    - **Адрес:** localhost:5433

- **PgAdmin**:
    - **Порт:** 5050
    - **Логин:** admin@ob11to.ru
    - **Пароль:** admin
    - **Адрес:** localhost:5050
    - **Примечание:** PgAdmin предоставляет веб-интерфейс для управления PostgreSQL.

- **Backend приложение**:
    - **Порт:** 8080
    - **Адрес:** localhost:8080
    - **OpenAPI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
    - **Конфигурация:** `application.yaml`, `application-docker.yaml`

### Примечания

- Приложение использует два файла конфигурации: `application.yaml` для локальной разработки и `application-docker.yaml` для Docker окружения. Убедитесь, что конфигурации соответствуют вашим требованиям.
- При необходимости изменения конфигурации, отредактируйте файлы `docker-compose.yaml`, `application.yaml` и `application-docker.yaml` согласно вашим потребностям.

### Важно!

- Перед использованием обязательно наличие VPN.

