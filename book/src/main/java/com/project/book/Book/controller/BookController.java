package com.project.book.Book.controller;


import com.project.book.Book.common.constant.ApiMappingPattern;
import com.project.book.Book.dto.request.BookRequestDto;
import com.project.book.Book.dto.request.BookRequestUpdateDto;
import com.project.book.Book.dto.response.BookResponseDto;
import com.project.book.Book.dto.response.ResponseDto;
import com.project.book.Book.entity.Category;
import com.project.book.Book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BOOK)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;


    // 책생성
    @PostMapping
    public ResponseEntity<ResponseDto<BookResponseDto>> createBook (@RequestBody BookRequestDto requestDto) {
        ResponseDto<BookResponseDto> result = bookService.createBook(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 책 전체 조회
    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        List<BookResponseDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }
    // 책 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        BookResponseDto book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // 책 제목 검색
    @GetMapping("/search/title")
    public ResponseEntity<List<BookResponseDto>> getBooksByTitleContaining(
            @RequestParam String keyword
    ) {
        List<BookResponseDto> books = bookService.getBooksByTitleContaining(keyword);
        return ResponseEntity.ok(books);
    }


    // 책 카테고리 검색
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BookResponseDto>> getBooksByCategory(@PathVariable Category category) {
        List<BookResponseDto> books = bookService.getBooksByCategory(category);
        return ResponseEntity.ok(books);
    }


    // 책 카테고리 별 제목 검색
    @GetMapping("/search/category-writer")
    public ResponseEntity<List<BookResponseDto>> getBooksByCategoryAndWriter(
            @RequestParam(required = false) Category category,
            @RequestParam String writer
    ) {
        List<BookResponseDto> books = bookService.getBooksByCategoryAndWriter(category, writer);
        return ResponseEntity.ok(books);
    }


    // 책 수정
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id, @RequestBody BookRequestUpdateDto  requestDto) {
        BookResponseDto updateBook = bookService.updateBook(id, requestDto);
        return ResponseEntity.ok(updateBook);
    }

    // 책삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}
