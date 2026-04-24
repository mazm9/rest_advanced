package edu.pja.sr._0.demo.rest;

import edu.pja.sr._0.demo.dto.AuthorDto;
import edu.pja.sr._0.demo.dto.BookDto;
import edu.pja.sr._0.demo.model.Author;
import edu.pja.sr._0.demo.model.Book;
import edu.pja.sr._0.demo.repo.AuthorRepository;
import edu.pja.sr._0.demo.repo.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public AuthorController(AuthorRepository authorRepository, BookRepository bookRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    private AuthorDto convertToDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    private BookDto convertBookToDto(Book book) {
        BookDto dto = modelMapper.map(book, BookDto.class);
        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
        }
        return dto;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAuthors() {
        List<AuthorDto> result = authorRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);

        if (author.isPresent()) {
            return new ResponseEntity<>(convertToDto(author.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> saveNewAuthor(@RequestBody AuthorDto authorDto) {
        if (authorDto.getFirstName() == null || authorDto.getFirstName().isBlank()) {
            return new ResponseEntity<>("Pole firstName nie może być puste", HttpStatus.BAD_REQUEST);
        }

        if (authorDto.getLastName() == null || authorDto.getLastName().isBlank()) {
            return new ResponseEntity<>("Pole lastName nie może być puste", HttpStatus.BAD_REQUEST);
        }

        Author author = modelMapper.map(authorDto, Author.class);
        author.setId(null);
        author = authorRepository.save(author);

        return new ResponseEntity<>(convertToDto(author), HttpStatus.CREATED);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long authorId, @RequestBody AuthorDto authorDto) {
        Optional<Author> currentAuthor = authorRepository.findById(authorId);

        if (currentAuthor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (authorDto.getFirstName() == null || authorDto.getFirstName().isBlank()) {
            return new ResponseEntity<>("Pole firstName nie może być puste", HttpStatus.BAD_REQUEST);
        }

        if (authorDto.getLastName() == null || authorDto.getLastName().isBlank()) {
            return new ResponseEntity<>("Pole lastName nie może być puste", HttpStatus.BAD_REQUEST);
        }

        Author author = currentAuthor.get();
        author.setFirstName(authorDto.getFirstName());
        author.setLastName(authorDto.getLastName());
        author.setNationality(authorDto.getNationality());

        authorRepository.save(author);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);

        if (author.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (Book book : author.get().getBooks()) {
            book.setAuthor(null);
            bookRepository.save(book);
        }

        authorRepository.deleteById(authorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{authorId}/books")
    public ResponseEntity<?> getAuthorBooks(@PathVariable Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);

        if (author.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<BookDto> result = author.get().getBooks()
                .stream()
                .map(this::convertBookToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<?> addBookToAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        Optional<Author> author = authorRepository.findById(authorId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (author.isEmpty() || book.isEmpty()) {
            return new ResponseEntity<>("Autor albo książka nie istnieje", HttpStatus.NOT_FOUND);
        }

        Book selectedBook = book.get();
        selectedBook.setAuthor(author.get());
        bookRepository.save(selectedBook);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<?> removeBookFromAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        Optional<Author> author = authorRepository.findById(authorId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (author.isEmpty() || book.isEmpty()) {
            return new ResponseEntity<>("Autor albo książka nie istnieje", HttpStatus.NOT_FOUND);
        }

        Book selectedBook = book.get();

        if (selectedBook.getAuthor() == null || !selectedBook.getAuthor().getId().equals(authorId)) {
            return new ResponseEntity<>("Ta książka nie jest przypisana do tego autora", HttpStatus.BAD_REQUEST);
        }

        selectedBook.setAuthor(null);
        bookRepository.save(selectedBook);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}