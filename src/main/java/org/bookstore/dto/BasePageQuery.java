package org.bookstore.dto;


import lombok.Getter;

@Getter
public abstract class BasePageQuery {
    protected static final int MIN_PAGE_NUM = 1;
    protected static final int MAX_PAGE_SIZE = 100;
    protected static final int MIN_PAGE_SIZE = 5;
    protected Integer pageNum = 1;
    protected Integer pageSize = 10;

    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }

    public void setPageNum(Integer pageNum) {
        if (pageNum == null || pageNum < MIN_PAGE_NUM) {
            this.pageNum = MIN_PAGE_NUM;
            return;
        }
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize < MIN_PAGE_SIZE) {
            this.pageSize = MIN_PAGE_SIZE;
            return;
        }
        if (pageSize > MAX_PAGE_SIZE) {
            this.pageSize = MAX_PAGE_SIZE;
            return;
        }
        this.pageSize = pageSize;
    }
}
