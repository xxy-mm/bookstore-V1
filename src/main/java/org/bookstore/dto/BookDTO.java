package org.bookstore.dto;

import lombok.Getter;
import lombok.Setter;
import org.bookstore.entity.Book;

@Setter
@Getter
public class BookDTO extends Book {

    private String categoryName;

}
