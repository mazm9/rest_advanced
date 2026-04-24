package edu.pja.sr._0.demo.repo;

import edu.pja.sr._0.demo.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAll();
}