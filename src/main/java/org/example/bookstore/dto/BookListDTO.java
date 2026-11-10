package org.example.bookstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class BookListDTO {
    private Long id;
    private String author;
    private String title;
    private BigDecimal price;
    private String cover;
    private String categoryName;
}
