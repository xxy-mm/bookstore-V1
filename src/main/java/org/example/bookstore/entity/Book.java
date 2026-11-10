package org.example.bookstore.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class Book {
    private Long id;
    private Long categoryId;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String cover;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
