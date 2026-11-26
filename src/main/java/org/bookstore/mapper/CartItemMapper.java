package org.bookstore.mapper;

import org.bookstore.entity.CartItem;

import java.util.List;

public interface CartItemMapper {


    int insert(CartItem cartItem);

    //    delete
    int delete(long carItemId);

    //    update
    int update(CartItem cartItem);

    //    select
    List<CartItem> selectList(long userId);
}
