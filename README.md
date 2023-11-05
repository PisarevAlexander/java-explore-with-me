# java-explore-with-me
Приложение позволяет пользователям делиться информацией об интересных событиях и находить компанию для участия в них. 
Свободное время — ценный ресурс. Ежедневно мы планируем, как его потратить — куда и с кем сходить. Сложнее всего в таком планировании поиск информации и переговоры. Нужно учесть много деталей: какие намечаются мероприятия, свободны ли в этот момент друзья, как всех пригласить и где собраться.

## Stack
- Java 11;
- Spring Boot;
- Maven;
- Lombok;
- PostgresSQL;
- LiquidBase
- Hibernate;
- Docker.

## Launch
Используйте docker-compose up для развертывания приложения.

## Services
- **statistic** - храниь количество просмотров и позволяет делать различные выборки для анализа работы приложения.
- **server** - основной сервиc;

### Server
- **public** - доступен без регистрации любому пользователю сети;
- **private** - доступен только авторизованным пользователям;
- **admin** - для администраторов сервиса.

### Statistic
Cобирает информацию. Во-первых, о количестве обращений пользователей к спискам событий и, во-вторых, о количестве запросов к подробной информации о событии. На основе этой информации формируется статистика о работе приложения.

## API
### Category
**Admin**

- POST admin/categories - добавить новую категорию
- DELETE admin/categories/{catId} - удалить категорию
- PATCH admin/categories/{catId} - обновить категорию
  
**Public**
  
- GET /categories - получить подборку категорий
- GET /categories/{catId} - получить категорию по id
  
### Comment
**Admin**

- DELETE /admin/comments/{commentId} - удалить комментарий
- PATCH /admin/comments/{commentId} - обновить комментарий
- GET /admin/comments/{eventId} - получить комментарии по id события
  
**Private**

- POST /users/{userId}/comments/{eventId} - новый комментарий
- PATCH /users/{userId}/comments/{commentId} - обновить комментарий
- DELETE /users/{userId}/comments/{commentId} - удалить комментарий
- GET /users/comments/{eventId} - получить комментарии по id события
  
**Public**

- GET /comments/{eventId} - получить комментарии по id события
### Compilation
**Admin**

- POST /admin/compilations - новая подборка событий
- DELETE /admin/compilations/{compId} - удалить подборку событий
- PATCH /admin/compilations/{compId} - обновить подборку событий
  
**Public**

- GET /compilations - получить подборку
- GET /compilations/{compId} - получить подборку событий по id
### Event
**Admin**

- GET admin/events - получить собылия
- GET admin/events/{eventId} - получить событие по id
  
**Private**

- GET users/{userId}/events - получить события по id пользователя
- POST users/{userId}/events - создать новое событие
- GET users/{userId}/events/{eventId} - получить событие
- PATCH users/{userId}/events/{eventId} - обновить событие
- GET users/{userId}/events/{eventId}/requests - получить запрос для события
- PATCH users/{userId}/events/{eventId}/requests - обновить запрос на событие
  
**Public**

- GET events - получить список событий
- GET events/{id} - получить событие по id
  
### Request
**Private**

- GET /users/{userId}/requests - получить запрос по id пользователя
- POST /users/{userId}/requests - добавить новый запрос
- PATCH /users/{userId}/requests/{requestId}/cancel - обновить запрос
  
### User
**Admin**

- POST /admin/users - добавить нового пользователя
- GET /admin/users - получить пользователя
- DELETE /admin/users/{userId} - удалить пользователя

## Swagger
- Cпецификация основного сервиса: [ewm-main-service-spec](https://raw.githubusercontent.com/yandex-praktikum/java-explore-with-me/main/ewm-main-service-spec.json)
- Cпецификация сервиса статистики [ewm-stats-service](https://raw.githubusercontent.com/yandex-praktikum/java-explore-with-me/main/ewm-stats-service-spec.json)

## ERD
### Server ERD
![Server ERD](https://github.com/PisarevAlexander/java-explore-with-me/blob/main/ERD_server.png?raw=true)
### Statistic ERD
![Statistic ERD](https://github.com/PisarevAlexander/java-explore-with-me/blob/main/ERD_stat.png?raw=true)
