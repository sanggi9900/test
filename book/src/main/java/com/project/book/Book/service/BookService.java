package com.project.book.Book.service;

import com.project.book.Book.common.constant.ResponseMessage;
import com.project.book.Book.dto.request.BookRequestDto;
import com.project.book.Book.dto.request.BookRequestUpdateDto;
import com.project.book.Book.dto.response.BookResponseDto;
import com.project.book.Book.dto.response.ResponseDto;
import com.project.book.Book.entity.Book;
import com.project.book.Book.entity.Category;
import com.project.book.Book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public ResponseDto<BookResponseDto> createBook(BookRequestDto requestDto) {
        Book book = new Book(
                null, requestDto.getBook_title(), requestDto.getBook_author(),
                requestDto.getBook_category()
        );

        Book savedBook = bookRepository.save(book);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, convertToResponseDto(savedBook));

    }

    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::convertToResponseDto )
                .collect(Collectors.toList());
    }

    public BookResponseDto getBookById(Long id) {
        try {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다: " + id));
            return  convertToResponseDto(book);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new BookResponseDto();
        }

    }

    public List<BookResponseDto> getBooksByTitleContaining(String keyword) {
        List<Book> books = bookRepository.findByTitleContaining(keyword);
        return books.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<BookResponseDto> getBooksByCategory(Category category) {
        return bookRepository.findByCategory(category)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }


    public List<BookResponseDto> getBooksByCategoryAndWriter(Category category, String writer) {
        List<Book> books;

        if(category == null) {
            books = bookRepository.findByWriter(writer);
        } else {
            books = bookRepository.findByCategoryAndWriter(category, writer);
        }

        return  books.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }


    public BookResponseDto updateBook(Long id, BookRequestUpdateDto updateDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다: " + id));
        book.setTitle(updateDto.getBook_title());
        book.setWriter(updateDto.getBook_author());
        book.setCategory(updateDto.getBook_category());

        Book updatedBook = bookRepository.save(book);
        return convertToResponseDto(updatedBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private BookResponseDto convertToResponseDto(Book book) {
        return new BookResponseDto(
                book.getId(),  book.getTitle(), book.getWriter(), book.getCategory()
        );
    }

}
