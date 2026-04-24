# REST Advanced – ćwiczenie 3

## Zaawansowane funkcje usługi REST

Projekt został wykonany w technologii **Java + Spring Boot**.  
Aplikacja realizuje operacje CRUD dla encji **Author** oraz **Book**, wraz z obsługą relacji między nimi.

---

## Technologie

- Java 26
- Spring Boot 3
- Spring Data JPA
- H2 Database
- Maven
- Postman

---

## Tematyka projektu

Aplikacja służy do zarządzania:

- autorami (`Author`)
- książkami (`Book`)

### Relacja między encjami

- jeden autor może mieć wiele książek
- jedna książka należy do jednego autora

---

## Uruchomienie projektu

```bash
mvn spring-boot:run
```

Aplikacja uruchamia się pod adresem:

`http://localhost:8080`

---

# Endpointy REST

## Authors

- `POST /api/authors`
- `GET /api/authors`
- `GET /api/authors/{id}`
- `PUT /api/authors/{id}`
- `DELETE /api/authors/{id}`

## Books

- `POST /api/books`
- `GET /api/books`
- `GET /api/books/{id}`

## Relacje

- `GET /api/authors/{id}/books`
- `GET /api/books/{id}/author`
- `POST /api/authors/{authorId}/books/{bookId}`
- `DELETE /api/authors/{authorId}/books/{bookId}`

---

# Testy Postman

## Dodanie autora
![Dodanie autora](screenshots/post_author.png)

## Dodanie drugiego autora
![Dodanie autora 2](screenshots/post_author_2.png)

## Pobranie wszystkich autorów
![Lista autorów](screenshots/get_authors.png)

## Pobranie autora po ID
![Autor po ID](screenshots/get_author_id.png)

## Edycja autora
![Edycja autora](screenshots/put_author.png)

## Usunięcie autora
![Usunięcie autora](screenshots/delete_author2.png)

## Dodanie książki
![Dodanie książki](screenshots/post_book.png)

## Dodanie drugiej książki
![Dodanie książki 2](screenshots/post_book2.png)

## Dodanie książki bez autora
![Bez autora](screenshots/post_book_noauthor.png)

## Pobranie wszystkich książek
![Lista książek](screenshots/get_books.png)

## Autor książki
![Autor książki](screenshots/get_author_bybook.png)

## Książki autora
![Książki autora](screenshots/get_book_byauthor.png)

## Nowe powiązanie
![Nowe powiązanie](screenshots/new_connection.png)

## Książki autora po zmianie
![Powiązanie](screenshots/get_connection.png)

## Autor książki po zmianie
![Powiązanie autora](screenshots/get_connection_author.png)

---

# Walidacja

## Puste firstName
![Walidacja](screenshots/val1.png)

## Pusty title
![Walidacja](screenshots/val2.png)

## Nieistniejący authorId
![Walidacja](screenshots/val3.png)

---

# Zastosowane mechanizmy

- REST API
- CRUD
- JPA
- Relacje OneToMany / ManyToOne
- Walidacja
- HTTP Status Codes
- JSON
- Postman

