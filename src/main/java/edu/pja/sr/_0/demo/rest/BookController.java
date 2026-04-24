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
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    private BookDto convertToDto(Book book) {
        BookDto dto = modelMapper.map(book, BookDto.class);
        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
        }
        return dto;
    }

    private AuthorDto convertAuthorToDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {
        List<BookDto> result = bookRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isPresent()) {
            return new ResponseEntity<>(convertToDto(book.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> saveNewBook(@RequestBody BookDto bookDto) {
        if (bookDto.getTitle() == null || bookDto.getTitle().isBlank()) {
            return new ResponseEntity<>("Pole title nie może być puste", HttpStatus.BAD_REQUEST);
        }

        if (bookDto.getPublishedYear() == null || bookDto.getPublishedYear() <= 0) {
            return new ResponseEntity<>("Pole publishedYear musi być większe od 0", HttpStatus.BAD_REQUEST);
        }

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setGenre(bookDto.getGenre());
        book.setPublishedYear(bookDto.getPublishedYear());

        if (bookDto.getAuthorId() != null) {
            Optional<Author> author = authorRepository.findById(bookDto.getAuthorId());

            if (author.isEmpty()) {
                return new ResponseEntity<>("Autor o podanym id nie istnieje", HttpStatus.BAD_REQUEST);
            }

            book.setAuthor(author.get());
        }

        book = bookRepository.save(book);
        return new ResponseEntity<>(convertToDto(book), HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody BookDto bookDto) {
        Optional<Book> currentBook = bookRepository.findById(bookId);

        if (currentBook.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (bookDto.getTitle() == null || bookDto.getTitle().isBlank()) {
            return new ResponseEntity<>("Pole title nie może być puste", HttpStatus.BAD_REQUEST);
        }

        if (bookDto.getPublishedYear() == null || bookDto.getPublishedYear() <= 0) {
            return new ResponseEntity<>("Pole publishedYear musi być większe od 0", HttpStatus.BAD_REQUEST);
        }

        Book book = currentBook.get();
        book.setTitle(bookDto.getTitle());
        book.setGenre(bookDto.getGenre());
        book.setPublishedYear(bookDto.getPublishedYear());

        if (bookDto.getAuthorId() != null) {
            Optional<Author> author = authorRepository.findById(bookDto.getAuthorId());

            if (author.isEmpty()) {
                return new ResponseEntity<>("Autor o podanym id nie istnieje", HttpStatus.BAD_REQUEST);
            }

            book.setAuthor(author.get());
        } else {
            book.setAuthor(null);
        }

        bookRepository.save(book);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        bookRepository.deleteById(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{bookId}/author")
    public ResponseEntity<?> getBookAuthor(@PathVariable Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (book.get().getAuthor() == null) {
            return new ResponseEntity<>("Książka nie ma przypisanego autora", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(convertAuthorToDto(book.get().getAuthor()), HttpStatus.OK);
    }
}