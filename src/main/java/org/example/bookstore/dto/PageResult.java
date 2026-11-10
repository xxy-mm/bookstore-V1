package org.example.bookstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageResult<T> {
    private List<T> list;
    private Integer totalPages;
    private Long totalRecords;
    private Integer pageNum;
    private Integer pageSize;
}
