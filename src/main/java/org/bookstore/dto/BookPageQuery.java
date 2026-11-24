package org.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookPageQuery extends BasePageQuery{
    private Long categoryId;
}
