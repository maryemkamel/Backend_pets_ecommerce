package org.ordermicroservice;

import lombok.AllArgsConstructor;
import org.ordermicroservice.entities.Address;
import org.ordermicroservice.entities.Order;
import org.ordermicroservice.entities.OrderItem;
import org.ordermicroservice.enums.OrderStatus;
import org.ordermicroservice.service.IOrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@AllArgsConstructor
public class OrderMicroserviceApplication {
    private final IOrderService orderService;
    public static void main(String[] args) {
        SpringApplication.run(OrderMicroserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            Order order = new Order();
            order.setId("1");
            order.setUserId(1L);

            Address deliveryAddress = new Address();
            OrderStatus orderStatus= OrderStatus.valueOf("CREATED");
            deliveryAddress.setFirstName("John");
            deliveryAddress.setLastName("Doe");
            deliveryAddress.setAddress("123 Main Street");
            deliveryAddress.setCity("Anytown");
            deliveryAddress.setCountry("Country");
            deliveryAddress.setEmail("achraflamsahel1@gmail.com");
           // deliveryAddress.setZipCode("12345");
            deliveryAddress.setPhone("123-456-7890");
            //deliveryAddress.setComment("Please leave the package at the front porch.");
            order.setDeliveryAddress(deliveryAddress);
            order.setStatus(orderStatus);
            List<OrderItem> items = new ArrayList<>();
            OrderItem item1 = new OrderItem();
            item1.setProductId("ABC123");
            item1.setLabel("Product 1");
            item1.setPrice("19.99");
            item1.setQty(2);
            items.add(item1);

            OrderItem item2 = new OrderItem();
            item2.setProductId("XYZ789");
            item2.setLabel("Product 2");
            item2.setPrice("29.99");
            item2.setQty(1);
            items.add(item2);
            order.setItems(items);
            order.setDate(new Date());
            order.setTotalPrice(69.97);
            orderService.save(order);
        };
    }
}
