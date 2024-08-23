package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.assembler.BookResourceAssembler;
import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.exception.BookNotFoundException;
import com.example.bookstoreapi.mapper.BookMapper;
import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private BookResourceAssembler bookResourceAssembler;

    @GetMapping
    public ResponseEntity<List<EntityModel<BookDTO>>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<EntityModel<BookDTO>> bookResources = books.stream()
                .map(book -> bookResourceAssembler.toModel(bookMapper.bookToBookDTO(book)))
                .collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "CustomHeaderValue");
        return new ResponseEntity<>(bookResources, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<BookDTO>> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            BookDTO bookDTO = bookMapper.bookToBookDTO(book.get());
            EntityModel<BookDTO> bookResource = bookResourceAssembler.toModel(bookDTO);
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Custom-Header", "AnotherCustomValue");
            return new ResponseEntity<>(bookResource, headers, HttpStatus.OK);
        } else {
            throw new BookNotFoundException(id);
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<BookDTO>> createBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = bookMapper.bookDTOToBook(bookDTO);
        Book createdBook = bookService.saveBook(book);
        BookDTO createdBookDTO = bookMapper.bookToBookDTO(createdBook);
        EntityModel<BookDTO> bookResource = bookResourceAssembler.toModel(createdBookDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "CreationHeaderValue");
        return new ResponseEntity<>(bookResource, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<BookDTO>> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        Book book = bookMapper.bookDTOToBook(bookDTO);
        Book updatedBook = bookService.updateBook(id, book);
        if (updatedBook == null) {
            throw new BookNotFoundException(id);
        }
        BookDTO updatedBookDTO = bookMapper.bookToBookDTO(updatedBook);
        EntityModel<BookDTO> bookResource = bookResourceAssembler.toModel(updatedBookDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "UpdateHeaderValue");
        return new ResponseEntity<>(bookResource, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "DeletionHeaderValue");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
