package org.bookstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageResult<T> {

    private List<T> list;
    private Long totalPages;
    private Long totalRecords;
    private Long pageNum;
    private Long pageSize;
}
