package com.review.ReviewMicroservice.Component;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
@NoArgsConstructor
public class ProductExistenceClient {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${product.existence.queue}")
    private String productExistenceQueue;

    public Boolean checkProductExistence(Long productId) {
        Object response = rabbitTemplate.convertSendAndReceive(productExistenceQueue, productId);
        String stringValue = ""+response;
        return Boolean.parseBoolean(stringValue);
    }
}