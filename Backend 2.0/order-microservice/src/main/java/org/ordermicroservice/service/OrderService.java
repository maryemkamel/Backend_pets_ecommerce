package org.ordermicroservice.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.ordermicroservice.client.UserServiceClient;
import org.ordermicroservice.dtos.OrderResponseDTO;
import org.ordermicroservice.dtos.PageRequestDTO;
import org.ordermicroservice.dtos.UserDTO;
import org.ordermicroservice.entities.Order;
import org.ordermicroservice.entities.OrderItem;
import org.ordermicroservice.enums.OrderStatus;
import org.ordermicroservice.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final DecimalFormat decimalFormat;
    private final UserServiceClient userServiceClient;
    @Override
    public List<OrderResponseDTO> findAllUserOrders() {
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
        orderRepository.findAll().forEach(order -> {
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
            orderResponseDTO.setOrder(order);
            UserDTO userDTO = userServiceClient.getUserByUserId(order.getUserId()).getBody();
            orderResponseDTO.setUser(userDTO);
            orderResponseDTOList.add(orderResponseDTO);
        });
        return orderResponseDTOList;
    }

    @Override
    public PageRequestDTO<Order> getAllOrdersPagination(Integer pageNumber, Integer pageSize, String sort) {
        Pageable pageable;
        if (sort != null) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sort);
        } else {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return new PageRequestDTO<>(
                orderPage.getContent(),
                orderPage.getNumber(),
                orderPage.getTotalPages(),
                (int) orderPage.getTotalElements()
        );
    }

    @Override
    public List<Order> findAllOrdersAuthenticated(Long userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId,pageable);
    }

    @Override
    public Order findOrderByOrderId(String id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Override
    public Order save(Order order) {
        // Génération d'une chaîne aléatoire de 10 chiffres
        //String randomNumber = String.format("%010d", new Random().nextInt(1000000000));
        String randomNumber = String.format("%06d", new Random().nextInt(1000000));


        // Génération d'une chaîne aléatoire de 3 lettres
        String randomLetters = RandomStringUtils.randomAlphabetic(3).toUpperCase();

        // Concaténation de l'ID
        String uniqueID = "ORD-" + randomNumber + randomLetters;
        order.setId(uniqueID);

        // Réglage de la date de la commande
        order.setDate(new Date());

        // Calcul et formatage du prix total
        String totalPriceFormat = decimalFormat.format(calculateTotalPrice(order.getItems()));
        order.setTotalPrice(Double.parseDouble(totalPriceFormat.replace(",", ".")));

        // Sauvegarde de la commande dans le repository
        return orderRepository.save(order);
    }


    @Override
    public long count() {
        return orderRepository.count();
    }

    private double calculateTotalPrice(List<OrderItem> items){
        if (items == null) return 0;
        return items.stream()
                .mapToDouble(orderItem -> orderItem.getQty() * Double.parseDouble(orderItem.getPrice()))
                .sum();
    }
    public void updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + orderId));

        order.setStatus(newStatus);
        orderRepository.save(order);
    }
}
