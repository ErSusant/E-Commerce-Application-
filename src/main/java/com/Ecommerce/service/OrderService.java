package com.Ecommerce.service;

import com.Ecommerce.dto.OrderDto;
import com.Ecommerce.entity.User;

import java.util.List;

public interface OrderService {
    public OrderDto addOrder(User user, OrderDto dto);
    public void deleteOrder(long orderId);
    public OrderDto updateOrder(User user,long orderId,OrderDto dto);
    public List<OrderDto> getAllOrder(int pageSize, int pageNo, String sortBy, String sortDir);
    public OrderDto getById(long orderId);
}
