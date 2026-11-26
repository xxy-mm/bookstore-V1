package org.bookstore.mapper;

import org.apache.ibatis.annotations.Param;
import org.bookstore.dto.BookDTO;
import org.bookstore.entity.Book;

import java.util.List;

public interface BookMapper {

    BookDTO selectById(Long id);

    List<Book> findAll();

    List<BookDTO> selectPageByCategory(@Param("limit") Integer limit, @Param("offset") Long offset,
                                       @Param("categoryId") Long categoryId);

    long selectCountByCategory(Long categoryId);

}
