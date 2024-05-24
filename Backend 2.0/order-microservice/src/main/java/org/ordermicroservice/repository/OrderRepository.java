package org.ordermicroservice.repository;

import org.ordermicroservice.entities.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order,String> {
    List<Order> findAllByUserId(Long userId, Pageable pageable);
}
