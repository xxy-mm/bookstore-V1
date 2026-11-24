package org.bookstore.dto;

import lombok.Getter;

/**
 * This DTO is designed for encapsulating page query parameters.
 * A traditional page query request should contain at least page number and
 * page size. The setters for them also handle invalid values, turn them into
 * valid ones.
 */
@Getter
public abstract class BasePageQuery {

    /**
     * The min page number should always be 1.
     */
    protected static final int MIN_PAGE_NUM = 1;
    /**
     * The max page size
     */
    protected static final int MAX_PAGE_SIZE = 100;
    /**
     * The min page size
     */
    protected static final int MIN_PAGE_SIZE = 5;
    /**
     * page number
     */
    protected Integer pageNum = 1;
    /**
     * page size
     */
    protected Integer pageSize = 10;

    /**
     * The controller can receive null or illegal values for page number
     * We handle the invalid values in the setter.
     *
     * @param pageNum the page number
     */
    public void setPageNum(Integer pageNum) {

        if (pageNum == null || pageNum < MIN_PAGE_NUM) {
            this.pageNum = MIN_PAGE_NUM;
            return;
        }
        this.pageNum = pageNum;
    }

    /**
     * The controller can receive null or illegal values for page size
     * We handle the invalid values in the setter.
     *
     * @param pageSize the page size
     */

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
