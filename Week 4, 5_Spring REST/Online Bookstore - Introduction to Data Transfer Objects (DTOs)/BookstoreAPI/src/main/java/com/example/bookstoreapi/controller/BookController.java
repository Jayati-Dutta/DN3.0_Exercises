package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.mapper.BookMapper;
import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookDTO> bookDTOs = books.stream()
                .map(bookMapper::bookToBookDTO)
                .collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "CustomHeaderValue");
        return new ResponseEntity<>(bookDTOs, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            BookDTO bookDTO = bookMapper.bookToBookDTO(book.get());
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Custom-Header", "AnotherCustomValue");
            return new ResponseEntity<>(bookDTO, headers, HttpStatus.OK);
        } else {
            throw new BookNotFoundException(id);
        }
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        Book book = bookMapper.bookDTOToBook(bookDTO);
        Book createdBook = bookService.saveBook(book);
        BookDTO createdBookDTO = bookMapper.bookToBookDTO(createdBook);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "CreationHeaderValue");
        return new ResponseEntity<>(createdBookDTO, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        Book book = bookMapper.bookDTOToBook(bookDTO);
        Book updatedBook = bookService.updateBook(id, book);
        if (updatedBook == null) {
            throw new BookNotFoundException(id);
        }
        BookDTO updatedBookDTO = bookMapper.bookToBookDTO(updatedBook);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "UpdateHeaderValue");
        return new ResponseEntity<>(updatedBookDTO, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "DeletionHeaderValue");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {
        List<Book> books = bookService.getBooksByTitleAndAuthor(title, author);
        List<BookDTO> bookDTOs = books.stream()
                .map(bookMapper::bookToBookDTO)
                .collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "SearchHeaderValue");
        return new ResponseEntity<>(bookDTOs, headers, HttpStatus.OK);
    }
}
