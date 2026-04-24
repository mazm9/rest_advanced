package edu.pja.sr._0.demo.repo;

import edu.pja.sr._0.demo.model.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findAll();
}