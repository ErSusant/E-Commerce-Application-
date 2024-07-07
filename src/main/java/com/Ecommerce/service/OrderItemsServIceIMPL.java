package com.Ecommerce.service;

import com.Ecommerce.dto.OrderItemsDto;
import com.Ecommerce.entity.Order;
import com.Ecommerce.entity.OrderItems;
import com.Ecommerce.entity.Product;
import com.Ecommerce.entity.User;
import com.Ecommerce.exception.ResourceNotFound;
import com.Ecommerce.repository.OrderItemsRepository;
import com.Ecommerce.repository.OrderRepository;
import com.Ecommerce.repository.ProductRepository;
import com.Ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class OrderItemsServIceIMPL implements OrderItemService{
    private OrderItemsRepository orderItemsRepository;
    private OrderRepository orderRepository;
   private UserRepository userRepository;
   private ProductRepository productRepository;

    public OrderItemsServIceIMPL(OrderItemsRepository orderItemsRepository, OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderItemsRepository = orderItemsRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
    public OrderItems dtoToEntity(OrderItemsDto orderItemsDto){
        OrderItems orderItems=new OrderItems();
        orderItems.setPrice(orderItemsDto.getPrice());
        orderItems.setProduct(orderItemsDto.getProduct());
        orderItems.setQuantity(orderItemsDto.getQuantity());
        orderItems.setOrder(orderItemsDto.getOrder());
        return orderItems;
    }
    public OrderItemsDto entityToDto(OrderItems orderItems){
        OrderItemsDto orderItemsDto=new OrderItemsDto();
        orderItemsDto.setId(orderItems.getId());
        orderItemsDto.setPrice(orderItems.getPrice());
        orderItemsDto.setProduct(orderItems.getProduct());
        orderItemsDto.setQuantity(orderItems.getQuantity());
        orderItemsDto.setOrder(orderItems.getOrder());
        return orderItemsDto;
    }

    @Override
    public OrderItemsDto addOrderItems(User user,long productId,long orderId, OrderItemsDto orderItemsDto) {
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if(byUsername.isPresent()){
            User user1 = byUsername.get();
            Optional<Product> byId = productRepository.findById(productId);
            if(byId.isPresent()){
                Product product = byId.get();
                orderItemsDto.setProduct(product);
                Optional<Order> byId1 = orderRepository.findById(orderId);
                if(byId1.isPresent()){
                    Order order = byId1.get();
                    orderItemsDto.setOrder(order);
                    OrderItems orderItems = dtoToEntity(orderItemsDto);
                    OrderItems save = orderItemsRepository.save(orderItems);
                    OrderItemsDto orderItemsDto1 = entityToDto(save);
                    return orderItemsDto1;
                }else{
                    throw new ResourceNotFound("OrderId Not Found: "+orderId);
                }

            }else{
                throw new ResourceNotFound("ProductId Not Found: "+productId);
            }
        }
        else{
            throw new ResourceNotFound("Username Not Found: "+user);
        }
    }
}
