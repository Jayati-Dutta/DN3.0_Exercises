package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "CustomHeaderValue");
        return new ResponseEntity<>(books, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Custom-Header", "AnotherCustomValue");
            return new ResponseEntity<>(book.get(), headers, HttpStatus.OK);
        } else {
            throw new BookNotFoundException(id);
        }
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookService.saveBook(book);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "CreationHeaderValue");
        return new ResponseEntity<>(createdBook, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        if (updatedBook == null) {
            throw new BookNotFoundException(id);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "UpdateHeaderValue");
        return new ResponseEntity<>(updatedBook, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "DeletionHeaderValue");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {
        List<Book> books = bookService.getBooksByTitleAndAuthor(title, author);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "SearchHeaderValue");
        return new ResponseEntity<>(books, headers, HttpStatus.OK);
    }
}
