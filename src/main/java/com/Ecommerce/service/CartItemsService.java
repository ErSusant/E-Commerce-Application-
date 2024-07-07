package com.Ecommerce.service;

import com.Ecommerce.dto.CartItemsDto;

public interface CartItemsService {
    public CartItemsDto addCartItems(CartItemsDto cartItemsDto,long cartId,long productId);
}
