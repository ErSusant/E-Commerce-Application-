package com.Ecommerce.service;

import com.Ecommerce.dto.CartDto;
import com.Ecommerce.entity.User;

import java.util.List;

public interface CartService {
    public CartDto addCart(User user, CartDto dto);
   // public CartDto updateCart(User user,long cartId,CartDto dto);
    public void deleteCart(User user,long cartId);
    public List<CartDto> getAllCart(User user);
    public List<CartDto>getAllUserCarts(User user);
}
