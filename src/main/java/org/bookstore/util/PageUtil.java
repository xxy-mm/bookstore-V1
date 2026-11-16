package org.bookstore.util;

import org.bookstore.dto.PageResult;

public class PageUtil {

    public static <T> PageResult<T> getPageResult(long pageSize, long pageNum, long totalCount) {

        long totalPages = totalCount == 0 ? 0 : ((totalCount - 1) / pageSize + 1);
        long actualPageNum = Math.min(Math.max(1, pageNum), totalPages);
        // pageNum should not less than 1. The servlet should handle the invalid situation.
        // but this is not an error that can break our business logic or our app
        // so we can just fix this, nothing bad will happen.
        actualPageNum = actualPageNum == 0 ? 1 : actualPageNum;

        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotalRecords(totalCount);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalPages(totalPages);
        pageResult.setPageNum(actualPageNum);

        return pageResult;
    }

}
