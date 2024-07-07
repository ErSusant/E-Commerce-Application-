package com.Ecommerce.controller;

import com.Ecommerce.dto.OrderDto;
import com.Ecommerce.entity.User;
import com.Ecommerce.service.OrderServiceIMPL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Order")
public class OrderController {
    private OrderServiceIMPL orderServiceIMPL;

    public OrderController(OrderServiceIMPL orderServiceIMPL) {
        this.orderServiceIMPL = orderServiceIMPL;
    }
    @PostMapping("/addOrder")
    public ResponseEntity<OrderDto>addOrder(@AuthenticationPrincipal User user, @RequestBody OrderDto dto){
        OrderDto orderDto = orderServiceIMPL.addOrder(user,dto);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }
    @DeleteMapping
    public ResponseEntity<String>deleteOrder(@RequestParam long orderId){
        orderServiceIMPL.deleteOrder(orderId);
        return new ResponseEntity<>("Record Deleted",HttpStatus.OK);
    }
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto>orderUpdate(
           @AuthenticationPrincipal User user, @PathVariable long orderId,@RequestBody OrderDto orderDto){
        OrderDto orderDto1 = orderServiceIMPL.updateOrder(user,orderId, orderDto);
        return new ResponseEntity<>(orderDto1,HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<OrderDto>>listOfOrders(
            @RequestParam(name="pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(name="pageNo",defaultValue = "5",required = false) int pageNo,
            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir){
        List<OrderDto> allOrder = orderServiceIMPL.getAllOrder(pageSize, pageNo, sortBy, sortDir);
        return new ResponseEntity<>(allOrder,HttpStatus.OK);
    }
    @GetMapping("/getOrderId")
    public ResponseEntity<OrderDto>getById(@RequestParam long orderId){
        OrderDto byId = orderServiceIMPL.getById(orderId);
        return new ResponseEntity<>(byId,HttpStatus.OK);
    }
}
