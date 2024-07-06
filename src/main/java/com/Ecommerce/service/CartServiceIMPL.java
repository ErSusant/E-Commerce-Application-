package com.Ecommerce.service;

import com.Ecommerce.dto.CartDto;
import com.Ecommerce.entity.Cart;
import com.Ecommerce.entity.User;
import com.Ecommerce.exception.ResourceNotFound;
import com.Ecommerce.repository.CartRepository;
import com.Ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceIMPL implements CartService {

    private CartRepository cartRepository;
   private UserRepository userRepository;

    public CartServiceIMPL(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }
    public Cart dtoToEntity(CartDto dto){
        Cart cart=new Cart();
        cart.setUser(dto.getUser());
        cart.setCreatedAt(dto.getCreatedAt());
        cart.setUpdateAt(dto.getUpdateAt());
        return cart;
    }
    public CartDto entityToDto(Cart cart){
        CartDto dto1=new CartDto();
        dto1.setId(cart.getId());
        dto1.setUser(cart.getUser());
        dto1.setCreatedAt(cart.getCreatedAt());
        dto1.setUpdateAt(cart.getUpdateAt());
        return dto1;
    }

    @Override
    public CartDto addCart(User user, CartDto dto) {
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if(byUsername.isPresent()){
            User user1 = byUsername.get();
            dto.setUser(user1);
            Cart cart = dtoToEntity(dto);
            Cart save = cartRepository.save(cart);
            CartDto cartDto = entityToDto(save);
            return cartDto;
        }
         else{
             throw new ResourceNotFound("User Not Found...!!!");
        }
    }
    @Override
    public void deleteCart(User user, long cartId) {
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if(byUsername.isPresent()){
            User user1 = byUsername.get();
            cartRepository.deleteById(cartId);
        }
    }
  //    Still not Work
//    @Override
//    public CartDto updateCart(User user,long cartId, CartDto dto) {
//        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
//        if(byUsername.isPresent()){
//            User user1 = byUsername.get();
//            Optional<Cart> byId = cartRepository.findById(cartId);
//            if(byId.isPresent()){
//                Cart cart = byId.get();
//                if (cart.getUser() != null) {
//                    cart.setUser(dto.getUser());
//                }
//                cart.setCreatedAt(dto.getCreatedAt());
//                cart.setUpdateAt(dto.getUpdateAt());
//                Cart save = cartRepository.save(cart);
//                CartDto cartDto = entityToDto(save);
//                return cartDto;
//            }else{
//                throw new ResourceNotFound("Cart Id Not Found With Id: "+cartId);
//            }
//        }else{
//            throw new ResourceNotFound("User Not Found");
//       }
//  }

    @Override
    public List<CartDto> getAllUserCarts(User user) {
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if(byUsername.isPresent()){
            User user1 = byUsername.get();
            List<Cart> all = cartRepository.findAll();
            List<CartDto> collect = all.stream().map(a -> entityToDto(a)).collect(Collectors.toList());
            return collect;
        }
        return null;
    }
    @Override
    public List<CartDto> getAllCart(User user) {
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername.isPresent()) {
            User user1 = byUsername.get();
            List<Cart> byUsersCrt = cartRepository.findByUsersCrt(user);
            List<CartDto> collect = byUsersCrt.stream().map(b -> entityToDto(b)).collect(Collectors.toList());
            return collect;
        }
        return null;
    }

}
