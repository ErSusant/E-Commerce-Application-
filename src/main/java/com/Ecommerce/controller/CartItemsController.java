package com.Ecommerce.controller;

import com.Ecommerce.dto.CartItemsDto;
import com.Ecommerce.service.CartItemsServiceIMPL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/CartItems")
public class CartItemsController {
    private CartItemsServiceIMPL cartItemsServiceIMPL;

    public CartItemsController(CartItemsServiceIMPL cartItemsServiceIMPL) {
        this.cartItemsServiceIMPL = cartItemsServiceIMPL;
    }
    @PostMapping("/addCartItems/{cartId}/{productId}")
    public ResponseEntity<CartItemsDto>addCartItems(
            @RequestBody CartItemsDto cartItemsDto, @PathVariable long cartId,@PathVariable long productId){
        CartItemsDto cartItemsDto1 = cartItemsServiceIMPL.addCartItems(cartItemsDto, cartId, productId);
        return new ResponseEntity<>(cartItemsDto1, HttpStatus.CREATED );
    }
}
