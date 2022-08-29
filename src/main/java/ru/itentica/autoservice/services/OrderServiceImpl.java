package ru.itentica.autoservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.itentica.autoservice.entities.*;
import ru.itentica.autoservice.repository.OrderRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

public class OrderServiceImpl /*implements IOrderService*/ {
/*
//    public static final int UNDEFINED_ID = -1;
    public static final Long UNDEFINED_ID = -1L;
    private final IPrincipalProvider principalProvider;
    //private final Map<Integer, Order> ordersMap = new HashMap<>();
    @Autowired
    OrderRepository orderRepository;

    public OrderServiceImpl(IPrincipalProvider principalProvider) {
        this.principalProvider = principalProvider;
    }

    @Override
    @Transactional
    public Order orderRegistration(String login, Order initialOrder, String comment) {
        Long orderId = IdProvider.getNextLongId();
        Long clientId = IdProvider.getNextLongId();

        Principal client = initialOrder.getClient();
        if (client.getId() == UNDEFINED_ID) {
            client.setId(clientId);

            // криво (не должны руками вызвыать сохранение, но когда создаётся неинициализированный клиент, то
            // приходится взять на себя заботу об обработке клиента)
            principalProvider.saveInitializedPrincipal(client);
        }

        // предположение что время создания ордера совпадает с времененм создания order history creation date
        Order order = new Order(orderId, initialOrder, createOrderHistoryItemInNewStatus(comment, initialOrder.getBeginDate()));
        orderRepository.save(order);
        //ordersMap.put(orderId, order);

        return order;
    }

    private OrderHistory createOrderHistoryItemInNewStatus(String comment, Date creationDate) {
        Long id = IdProvider.getNextLongId();
        OrderHistory orderHistory = new OrderHistory();
        OrderHistoryItem orderHistoryItem = new OrderHistoryItem(id, OrderStatus.NEW, comment, creationDate);
        orderHistory.addOrderHistoryItem(orderHistoryItem);

        return orderHistory;
    }

    @Override
    @Transactional
    public Order moveOrderToWork(Long orderId, int masterId) throws Throwable {
        Order order = getOrder(orderId);
        //Order order = ordersMap.get(orderId);
        Principal master = principalProvider.getWorker(masterId);
        OrderHistory orderHistory = order.getOrderHistory();
        orderHistory.addOrderHistoryItem(new OrderHistoryItem(IdProvider.getNextLongId(), OrderStatus.ACCEPTED,
                "Order was accepted", new Date()));
        order.setMaster(master);

        return order;
    }

//    @Override
//    public Order addWorkItemsToOrder(int orderId, List<WorkItem> workItems) {
//        return null;
//    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus, String comment) throws Throwable {
        Order order = getOrder(orderId);

        //Order order = ordersMap.get(orderId);

        OrderHistory orderHistory = order.getOrderHistory();
        orderHistory.addOrderHistoryItem(new OrderHistoryItem(IdProvider.getNextLongId(), orderStatus,
                comment, new Date()));

        if (orderStatus.equals(OrderStatus.DONE))
            order.setEndDate(new Date());

        return order;
    }

    @Override
    public OrderStatus getOrderStatus(Long orderId) {
        return null;
    }

    @Override
    public Order createInitialOrder(String reason, String comment, Principal client) {
        return new Order(UNDEFINED_ID, reason, new Date(), null, comment, Collections.<WorkItem>emptyList(),
                null, client, null, null);
    }

    @Override
    @Transactional
    public Order getOrder(Long orderId) throws Throwable {
        return orderRepository.findById(orderId)
                .orElseThrow((Supplier<Throwable>) () -> new IllegalArgumentException("Incorrect orderId"));
       // return ordersMap.get(orderId);
    }

    @Override
    public List<Order> getAllOrders() {
        Iterable<Order> source = orderRepository.findAll();
        List<Order> target = new ArrayList<>();
        source.forEach(target::add);
        return target;
        //return new ArrayList<>(ordersMap.values());

    }

    @Override
    @Transactional
    public void updateOrder(Order order) throws Exception {
        if (order.getId() == UNDEFINED_ID)
            throw new Exception("Order should have id");

        orderRepository.save(order);
        //ordersMap.put(order.getId(), order);
    }*/
}
