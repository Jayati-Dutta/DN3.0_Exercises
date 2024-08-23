package com.example.bookstoreapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class BookDTO {
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Author is mandatory")
    private String author;

    @NotNull(message = "Price is mandatory")
    @Min(value = 0, message = "Price must be a positive number")
    private Double price;

    @NotBlank(message = "ISBN is mandatory")
    private String isbn;
}
