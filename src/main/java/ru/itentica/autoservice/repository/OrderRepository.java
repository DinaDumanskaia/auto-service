package ru.itentica.autoservice.repository;

import org.springframework.stereotype.Repository;
import ru.itentica.autoservice.entities.Order;

import java.util.List;

public interface OrderRepository {
    void save(Order order);

    void update(Order order);

    Order findById(Long id);

    List<Order> findAll();

    void deleteAll();
}
