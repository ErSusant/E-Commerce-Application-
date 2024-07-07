package com.Ecommerce.service;

import com.Ecommerce.dto.OrderDto;
import com.Ecommerce.entity.Order;
import com.Ecommerce.entity.User;
import com.Ecommerce.exception.ResourceNotFound;
import com.Ecommerce.repository.OrderRepository;
import com.Ecommerce.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceIMPL implements OrderService{
    private OrderRepository orderRepository;
   private UserRepository userRepository;
    public OrderServiceIMPL(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }
    public Order dtoToEntity(OrderDto dto){
        Order order=new Order();
        order.setStatus(dto.getStatus());
        order.setUser(dto.getUser());
        order.setCreatedAt(dto.getCreatedAt());
        order.setTotalPrice(dto.getTotalPrice());
        order.setUpdatedAt(dto.getUpdatedAt());
        return order;
    }
    public OrderDto entityToDto(Order order){
        OrderDto dto1=new OrderDto();
        dto1.setId(order.getId());
        dto1.setStatus(order.getStatus());
        dto1.setUser(order.getUser());
        dto1.setUpdatedAt(order.getUpdatedAt());
        dto1.setCreatedAt(order.getCreatedAt());
        dto1.setTotalPrice(order.getTotalPrice());
        return dto1;
    }

    @Override
    public OrderDto addOrder(User user, OrderDto dto) {
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if(byUsername.isPresent()){
            User user1 = byUsername.get();
            if(user1.getUsername()!=null){
                User user11 = userRepository.findByUsername(user.getUsername()).get();
                dto.setUser(user11);
                Order order = dtoToEntity(dto);
                Order save = orderRepository.save(order);
                OrderDto orderDto = entityToDto(save);
                return orderDto;
            }

        }
        throw new ResourceNotFound("User Not Found");
    }

    @Override
    public void deleteOrder(long orderId) {
        orderRepository.deleteById(orderId);


    }

    @Override
    public OrderDto updateOrder(User user,long orderId, OrderDto dto) {
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if(byUsername.isPresent()){
            User user1 = byUsername.get();
            Optional<Order> byId = orderRepository.findById(orderId);
            if(byId.isPresent()){
                User user11 = userRepository.findByUsername(user.getUsername()).get();
                dto.setUser(user11);
                Order order = byId.get();
                order.setStatus(dto.getStatus());
                order.setUser(dto.getUser());
                order.setCreatedAt(dto.getCreatedAt());
                order.setTotalPrice(dto.getTotalPrice());
                order.setUpdatedAt(dto.getUpdatedAt());
                Order save = orderRepository.save(order);
                OrderDto orderDto = entityToDto(save);
                return orderDto;
            }else{
                throw new ResourceNotFound("OrderId Not Found: "+orderId);
            }
        }
       else{
           throw new ResourceNotFound("User Not Found");
        }
    }

    @Override
    public List<OrderDto> getAllOrder(int pageSize,int pageNo,String sortBy,String sortDir) {
        Pageable pageable=null;
        if(sortDir.equalsIgnoreCase("asc")){
            pageable = PageRequest.of(pageSize, pageNo, Sort.by(sortBy).ascending());
        }
        if(sortDir.equalsIgnoreCase("desc")){
            pageable = PageRequest.of(pageSize, pageNo, Sort.by(sortBy).descending());
        }
        Page<Order> page = orderRepository.findAll(pageable);
        List<Order> content = page.getContent();
        List<OrderDto> collect = content.stream().map(c -> entityToDto(c)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public OrderDto getById(long orderId) {
        Optional<Order> byId = orderRepository.findById(orderId);
        if(byId.isPresent()){
            Order order = byId.get();
            OrderDto orderDto = entityToDto(order);
            return orderDto;
        }
          throw new ResourceNotFound("OrderId Not Found: "+orderId);
    }
}
