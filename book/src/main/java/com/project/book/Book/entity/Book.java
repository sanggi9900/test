package com.project.book.Book.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="book_title", nullable = false, length = 50)
    private String title;

    @Column(name="book_author", nullable = false,length = 100)
    private String writer;

    @Enumerated(EnumType.STRING)
    @Column(name="book_category", nullable = false)
    private Category category;

}
