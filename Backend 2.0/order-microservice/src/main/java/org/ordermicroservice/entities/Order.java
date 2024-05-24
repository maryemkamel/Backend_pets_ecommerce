package org.ordermicroservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ordermicroservice.enums.OrderStatus;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Data
@Document(collection = "order")
public class Order {
    private String id;
    private Long userId;
    private Address deliveryAddress;
    private List<OrderItem> items;
    private Date date;
    private double totalPrice;
    private OrderStatus status;

}
