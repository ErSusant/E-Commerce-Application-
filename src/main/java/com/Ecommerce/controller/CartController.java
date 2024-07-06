package com.Ecommerce.controller;

import com.Ecommerce.dto.CartDto;
import com.Ecommerce.entity.User;
import com.Ecommerce.service.CartServiceIMPL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Cart")
public class CartController {
    private CartServiceIMPL cartServiceIMPL;

    public CartController(CartServiceIMPL cartServiceIMPL) {
        this.cartServiceIMPL = cartServiceIMPL;
    }
    @PostMapping("/addCart")
    public ResponseEntity<CartDto>addCart(@AuthenticationPrincipal User user, @RequestBody CartDto dto){
        CartDto cartDto = cartServiceIMPL.addCart(user,dto);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }
    @DeleteMapping
    public ResponseEntity<String>deleteCart(
            @AuthenticationPrincipal User user,@RequestParam long cartId){
        cartServiceIMPL.deleteCart(user, cartId);
        return new ResponseEntity<>("Record Deleted",HttpStatus.OK);
    }

    @PutMapping("/updateCart/{cartId}")
    public ResponseEntity<CartDto>updateCart(
     @AuthenticationPrincipal User user,@PathVariable long cartId,
     @RequestBody CartDto dto){
        CartDto cartDto = cartServiceIMPL.updateCart(user, cartId, dto);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }
    @GetMapping("/getAllCarts")
    public ResponseEntity<List<CartDto>>getAllUserCart(@AuthenticationPrincipal User user){
        List<CartDto> allCart = cartServiceIMPL.getAllCart(user);
        return new ResponseEntity<>(allCart,HttpStatus.OK);
    }
    @GetMapping("/getCartsByUser")
    public ResponseEntity<List<CartDto>>findByUserCart(@AuthenticationPrincipal User user){
        List<CartDto> allCart = cartServiceIMPL.getAllUserCarts(user);
        return new ResponseEntity<>(allCart,HttpStatus.OK);
    }
}
