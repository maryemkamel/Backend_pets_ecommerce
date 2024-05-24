package org.ordermicroservice.service;

import org.ordermicroservice.dtos.OrderResponseDTO;
import org.ordermicroservice.dtos.PageRequestDTO;
import org.ordermicroservice.entities.Order;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IOrderService {
    List<OrderResponseDTO> findAllUserOrders();
    PageRequestDTO<Order> getAllOrdersPagination(Integer pageNumber, Integer pageSize, String sort);
    List<Order> findAllOrdersAuthenticated(Long userId, Pageable pageable);
    Order findOrderByOrderId(String id);
    Order save(Order order);
    long count();
}
