package org.ordermicroservice.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.ordermicroservice.app.OrderApp;
import org.ordermicroservice.client.UserServiceClient;
import org.ordermicroservice.dtos.OrderResponseDTO;
import org.ordermicroservice.dtos.PageRequestDTO;
import org.ordermicroservice.dtos.UserDTO;
import org.ordermicroservice.entities.Address;
import org.ordermicroservice.entities.Order;
import org.ordermicroservice.enums.OrderStatus;
import org.ordermicroservice.repository.OrderRepository;
import org.ordermicroservice.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class OrderController {
    private final OrderApp orderApp;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UserServiceClient userServiceClient;

    @GetMapping("/allOrders")
    private List<OrderResponseDTO> handleGetOrdersUsers() {
        return orderService.findAllUserOrders();
    }

    @GetMapping(value = "/{pageNumber}/{pageSize}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private PageRequestDTO<Order> pagination(@PathVariable Integer pageNumber, @PathVariable Integer pageSize, String sort) {
        return orderService.getAllOrdersPagination(pageNumber, pageSize, sort);
    }

    @GetMapping
    private PageRequestDTO<Order> handleGetOrders(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        String page = request.getParameter("page");
        if (!email.isEmpty() && !page.isEmpty())
            return orderApp.getAll(email, request.getParameter("page"));
        return null;
    }

    @PostMapping("/save")
    public ResponseEntity<?> handleSave(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.save(order));
    }

    @PostMapping("/delivery-address")
    public String handleDeliveryAddress(@RequestBody Address address, HttpSession session) {
        Order order = (Order) session.getAttribute("cart");
        order.setDeliveryAddress(address);
        return "";
    }

    @GetMapping("/all")
    public PageRequestDTO<Order> getAll(@RequestParam String email, @RequestParam String page) {
        int pageToInt = calculatePage(page);
        long count = orderService.count();
        int totalPages = (int) ((count + 9) / 10);
        UserDTO user = userServiceClient.getUserByEmail(email).getBody();
        assert user != null;
        List<Order> orders = orderRepository.findAllByUserId(user.getUserId(), PageRequest.of(pageToInt, 10, Sort.by(Sort.Direction.DESC, "date")));
        return new PageRequestDTO<>(orders, pageToInt, orders.isEmpty() ? 1 : totalPages, (int) count);
    }

    private int calculatePage(String page) {
        try {
            return Integer.parseInt(page);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String id, @RequestParam OrderStatus newStatus) {
        try {
            orderService.updateOrderStatus(id, newStatus);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update order status", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
