package com.Ecommerce.controller;

import com.Ecommerce.dto.OrderItemsDto;
import com.Ecommerce.entity.User;
import com.Ecommerce.service.OrderItemsServIceIMPL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Items")
public class OrderItemsController {
    private OrderItemsServIceIMPL orderItemsServIceIMPL;

    public OrderItemsController(OrderItemsServIceIMPL orderItemsServIceIMPL) {
        this.orderItemsServIceIMPL = orderItemsServIceIMPL;
    }
    @PostMapping("/addOrderItems/{orderId}/{productId}")
    public ResponseEntity<OrderItemsDto>addOrderItems(
            @AuthenticationPrincipal User user,@PathVariable long orderId,@PathVariable long productId, @RequestBody OrderItemsDto orderItemsDto){
        OrderItemsDto orderItemsDto1 = orderItemsServIceIMPL.addOrderItems(user,productId,orderId,orderItemsDto);
        return new ResponseEntity<>(orderItemsDto1, HttpStatus.CREATED);
    }
}
