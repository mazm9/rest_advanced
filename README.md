# REST Advanced – ćwiczenie 3

## Zaawansowane funkcje usługi REST

Projekt został wykonany w technologii Java + Spring Boot. Aplikacja realizuje operacje CRUD dla encji **Author** oraz **Book**, wraz z obsługą relacji między nimi.

## Technologie

- Java 26
- Spring Boot 3
- Spring Data JPA
- H2 Database
- Maven
- Postman

## Uruchomienie

```bash
mvn spring-boot:run
```

Aplikacja działa pod adresem:

`http://localhost:8080`

## Endpointy

### Authors
- POST /api/authors
- GET /api/authors
- GET /api/authors/{id}
- PUT /api/authors/{id}
- DELETE /api/authors/{id}

### Books
- POST /api/books
- GET /api/books
- GET /api/books/{id}

### Relacje
- GET /api/authors/{id}/books
- GET /api/books/{id}/author
- POST /api/authors/{authorId}/books/{bookId}
- DELETE /api/authors/{authorId}/books/{bookId}

## Testy Postman


