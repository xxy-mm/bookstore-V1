package org.bookstore.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CartItem {
    private Long id;
    private Long userId;
    private Long bookId;
    private Integer quantity;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
