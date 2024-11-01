package com.project.book.Book.dto.response;


import com.project.book.Book.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {
    private Long id;
    private String book_title;
    private String book_author;
    private Category book_category;
}
