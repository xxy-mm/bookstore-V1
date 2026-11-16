package org.bookstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class BookDTO {

    private Long id;
    private String author;
    private String title;
    private BigDecimal price;
    private String cover;
    private String categoryName;
}
