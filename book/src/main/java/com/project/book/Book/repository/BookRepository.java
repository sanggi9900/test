package com.project.book.Book.repository;

import com.project.book.Book.entity.Book;
import com.project.book.Book.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContaining(String keyword);
    List<Book> findByCategory(Category category);
    List<Book> findByCategoryAndWriter(Category category, String writer);
    List<Book> findByWriter(String writer);
}
