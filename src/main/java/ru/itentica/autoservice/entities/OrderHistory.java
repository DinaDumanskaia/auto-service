package ru.itentica.autoservice.entities;

import ru.itentica.autoservice.repository.OrderRepositoryImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class OrderHistory {
    private Long id;
    private final List<OrderHistoryItem> orderHistoryItemList = new ArrayList<>();


    public OrderHistoryItem getLastOrderHistoryItem() throws Exception {
        if (orderHistoryItemList.isEmpty())
            throw new Exception("Order history is empty");

        return orderHistoryItemList.get(orderHistoryItemList.size() - 1);
    }

    public void addOrderHistoryItem(OrderHistoryItem orderHistoryItem) {
        orderHistoryItemList.add(orderHistoryItem);
    }

    public List<OrderHistoryItem> getOrderHistoryItemList() {
        return Collections.unmodifiableList(orderHistoryItemList);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
