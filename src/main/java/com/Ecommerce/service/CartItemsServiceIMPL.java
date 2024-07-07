package com.Ecommerce.service;

import com.Ecommerce.dto.CartItemsDto;
import com.Ecommerce.dto.OrderItemsDto;
import com.Ecommerce.entity.Cart;
import com.Ecommerce.entity.CartItems;
import com.Ecommerce.entity.Product;
import com.Ecommerce.entity.User;
import com.Ecommerce.exception.ResourceNotFound;
import com.Ecommerce.repository.CartItemsRepository;
import com.Ecommerce.repository.CartRepository;
import com.Ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemsServiceIMPL implements  CartItemsService{
private CartItemsRepository cartItemsRepository;
private CartRepository cartRepository;
private ProductRepository productRepository;

    public CartItemsServiceIMPL(CartItemsRepository cartItemsRepository, CartRepository cartRepository, ProductRepository productRepository) {
        this.cartItemsRepository = cartItemsRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }
    public CartItems dtoToEntity(CartItemsDto cartItemsDto){
        CartItems cartItems=new CartItems();
        cartItems.setCart(cartItemsDto.getCart());
        cartItems.setProduct(cartItemsDto.getProduct());
        cartItems.setQuantity(cartItemsDto.getQuantity());
        return cartItems;
    }
    public CartItemsDto entityToDto(CartItems cartItems){
        CartItemsDto dto1=new CartItemsDto();
        dto1.setId(cartItems.getId());
        dto1.setProduct(cartItems.getProduct());
        dto1.setQuantity(cartItems.getQuantity());
        dto1.setCart(cartItems.getCart());
        return dto1;
    }

    @Override
    public CartItemsDto addCartItems(CartItemsDto cartItemsDto,long cartId,long productId) {
        Optional<Product> byId = productRepository.findById(productId);
        if(byId.isPresent()){
            Product product = byId.get();
            cartItemsDto.setProduct(product);
            Optional<Cart> byId1 = cartRepository.findById(cartId);
            if(byId1.isPresent()){
                Cart cart = byId1.get();
                cartItemsDto.setCart(cart);
                CartItems cartItems = dtoToEntity(cartItemsDto);
                CartItems save = cartItemsRepository.save(cartItems);
                CartItemsDto cartItemsDto1 = entityToDto(save);
                return cartItemsDto1;

            }else {
                throw new ResourceNotFound("CartId Not found With : "+cartId);
            }
        }

       else {
           throw new ResourceNotFound("ProductId Not Found With: "+productId);
        }
    }
}
