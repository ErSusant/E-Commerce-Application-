package com.Ecommerce.repository;

import com.Ecommerce.dto.CartDto;
import com.Ecommerce.entity.Cart;
import com.Ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user=:user")
    List<Cart>findByUsersCrt(@Param("user") User user);
}