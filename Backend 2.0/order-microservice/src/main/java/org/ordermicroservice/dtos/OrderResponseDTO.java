package org.ordermicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ordermicroservice.entities.Order;
@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {

    private UserDTO user;
    private Order order;

}
