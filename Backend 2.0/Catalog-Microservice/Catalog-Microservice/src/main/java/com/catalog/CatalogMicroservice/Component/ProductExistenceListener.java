package com.catalog.CatalogMicroservice.Component;
import com.catalog.CatalogMicroservice.Repository.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductExistenceListener {
    @Autowired
    private final ProductRepo productRepo;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @RabbitListener(queues = "${product.existence.queue}")
    @SendTo("${product.existence.response.queue}")
    public String checkProductExistence(Long productId) {
        String response = String.valueOf(productRepo.existsById(productId));
        return response;
    }
}