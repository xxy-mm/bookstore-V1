package org.bookstore.mapper;

import org.apache.ibatis.annotations.Param;
import org.bookstore.dto.BookDTO;
import org.bookstore.entity.Book;

import java.util.List;

public interface BookMapper {

    Book findById(long id);

    List<Book> findAll();

    List<BookDTO> findAllByCategory(@Param("limit") Long limit, @Param("offset") Long offset,
                                    @Param("categoryIds") List<Long> categoryId);

    long queryCountByCategoryId(@Param("categoryIds") List<Long> categoryId);
}
