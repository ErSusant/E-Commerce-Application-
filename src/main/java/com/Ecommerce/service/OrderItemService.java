package com.Ecommerce.service;

import com.Ecommerce.dto.OrderItemsDto;
import com.Ecommerce.entity.User;

public interface OrderItemService {
    public OrderItemsDto addOrderItems(User user,long orderId,long productId,OrderItemsDto orderItemsDto);
}
