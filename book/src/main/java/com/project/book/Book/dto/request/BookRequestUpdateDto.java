package com.project.book.Book.dto.request;

import com.project.book.Book.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestUpdateDto {

    private String book_title;
    private String book_author;
    private Category book_category;
}
