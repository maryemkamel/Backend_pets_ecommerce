package org.ordermicroservice.app;

import lombok.RequiredArgsConstructor;
import org.ordermicroservice.client.UserServiceClient;
import org.ordermicroservice.dtos.PageRequestDTO;
import org.ordermicroservice.dtos.UserDTO;
import org.ordermicroservice.entities.Order;
import org.ordermicroservice.repository.OrderRepository;
import org.ordermicroservice.service.IOrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderApp {
    private final IOrderService iOrderService;
    private final OrderRepository  orderRepository;
    private final UserServiceClient userServiceClient;

    public PageRequestDTO<Order> getAll(String email, String page) {
        int pageToInt = calculatePage(page);
        long count = iOrderService.count();
        int totalPages = (int) ((count + 9) / 10);
        UserDTO user = userServiceClient.getUserByEmail(email).getBody();
        assert user != null;
        List<Order> orders = orderRepository.findAllByUserId(user.getUserId(), PageRequest.of(pageToInt, 10, Sort.by(Sort.Direction.DESC, "date")));
        return new PageRequestDTO<>(orders, pageToInt, orders.isEmpty() ? 1 : totalPages, (int) count);
    }

    private int calculatePage(String page) {
        int pageToInt = 0;
        if (page != null && isDigit(page)) {
            pageToInt = Integer.parseInt(page);
            if (pageToInt < 0) pageToInt = 0;
        }
        return pageToInt;
    }

    private boolean isDigit(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
