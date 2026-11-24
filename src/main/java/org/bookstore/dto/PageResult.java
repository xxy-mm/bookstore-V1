package org.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    private List<T> list;
    private Integer totalPages;
    private Long totalRecords;
    private Integer pageNum;
    private Integer pageSize;

    public static <T> PageResult<T> create(BasePageQuery query,
                                           long totalCount) {

        int totalPages = totalCount == 0 ? 0 :
                (int) ((totalCount - 1) / query.getPageSize() + 1);
        int actualPageNum = totalPages <= 1 ? 1 :
                Math.min(query.getPageNum(),
                        totalPages);

        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotalRecords(totalCount);
        pageResult.setPageSize(query.getPageSize());
        pageResult.setTotalPages(totalPages);
        pageResult.setPageNum(actualPageNum);

        return pageResult;
    }

    public PageResult<T> empty(BasePageQuery query) {

        return new PageResult<>(List.of(), 0, 0L, query.getPageNum(),
                query.getPageSize());
    }

    public Long getOffset() {

        return (long) pageSize * (pageNum - 1);
    }

}
