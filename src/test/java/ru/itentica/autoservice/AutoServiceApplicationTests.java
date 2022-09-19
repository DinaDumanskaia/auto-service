package ru.itentica.autoservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.itentica.autoservice.entities.*;
import ru.itentica.autoservice.services.*;

import javax.sql.DataSource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.itentica.autoservice.services.JDBCPrincipalServiceImpl.WORKER_ID;

@Import(TestConfigurationTest.class)
@SpringBootTest()
class AutoServiceApplicationTests {
// 1. создать конфигурацию, чтобы можно было использовать автоматическое связывание компонентов репозитариев и сервисов
// 2. в тех местах тестов, которые используют репозитарии, заменить эти вызовы обёрнутыми вызовами сервисов
    @Autowired
    private PrincipalService principalService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DataSource dataSource;



    @Test
    @Transactional
    void orderRegistrationTest() throws Throwable { // проверить, что нет эксепшена
        Order registeredOrder = registerNewOrder();
        assertEquals(registeredOrder.getOrderHistoryItemsList().get(registeredOrder.getOrderHistoryItemsList().size() - 1).getStatus(), OrderStatus.NEW);
    }

    private Order registerNewOrder() throws Throwable {
        // создаём все principal'ов участвующих в тесте
        // используем их в качестве аргументов. рассчитываем на то, что база пустая

        principalService.saveInitializedPrincipal(new Principal(IdProvider.getNextLongId(), "Виталий", "555-542", Position.MASTER, new Date(), "Work place"));
        Principal clientWithoutId = createPrincipal("Рита", "555-555", "Sweet home");
        Principal admin  = createPrincipal("Василий", "555-552", "Work place");

        Order initialOrder = orderService.createInitialOrder("Требуется ремонт ходовой", "", clientWithoutId);
        return  orderService.orderRegistration(admin.getName(), initialOrder, null);
    }

    private Principal createPrincipal(String name, String phoneNumber, String address) {
        Principal principal = principalService.getClientWithoutId(name, phoneNumber, address);
        principal.setId(IdProvider.getNextLongId());
        principalService.saveInitializedPrincipal(principal);
        return principal;
    }

 /*   @Test
    void moveOrderToWorkTest() throws Throwable { // проверить, что нет эксепшена
        Order registeredOrder = registerNewOrder();
        Order acceptedOrder = orderService.moveOrderToWork(registeredOrder.getId(), WORKER_ID);
        assertEquals(acceptedOrder.getOrderHistoryItemsList().get(registeredOrder.getOrderHistoryItemsList().size() - 1).getStatus(), OrderStatus.ACCEPTED);
    }

    @Test
    void addWorkItemsToOrderTest() throws Throwable {
        Order registeredOrder = registerNewOrder();
        Order acceptedOrder = orderService.moveOrderToWork(registeredOrder.getId(), WORKER_ID);

        WorkItem workItem1 = new WorkItem(1, PriceItem.WHEEL_REPLACE);
        WorkItem workItem2 = new WorkItem(5, PriceItem.OIL_NUMBER_ONE);
        Long id = acceptedOrder.getId();
        Order orderToAddWorkItems = orderService.getOrder(id);
        assertEquals(0, orderToAddWorkItems.getWorkItems().size());

        orderToAddWorkItems.addWorkItem(workItem1, workItem2);
        orderService.updateOrder(orderToAddWorkItems);
        assertEquals(2, orderService.getOrder(id).getWorkItems().size());
    }

    @Test
    void updateOrderStatusSetAndGetFunctionalityTest() throws Throwable {
        Order registeredOrder = registerNewOrder();
        Order acceptedOrder = orderService.moveOrderToWork(registeredOrder.getId(), WORKER_ID);
        Long id = acceptedOrder.getId();

        orderService.updateOrderStatus(id, OrderStatus.IN_QUEUE, "Moved to queue");
        assertEquals(orderService.getOrder(id).getOrderHistoryItemsList().get(registeredOrder.getOrderHistoryItemsList().size() - 1).getStatus(), OrderStatus.IN_QUEUE);

        orderService.updateOrderStatus(id, OrderStatus.IN_WORK, "Moved to work");
        assertEquals(orderService.getOrder(id).getOrderHistoryItemsList().get(registeredOrder.getOrderHistoryItemsList().size() - 1).getStatus(), OrderStatus.IN_WORK);

        orderService.updateOrderStatus(id, OrderStatus.DONE, "Work is done");
        assertEquals(orderService.getOrder(id).getOrderHistoryItemsList().get(registeredOrder.getOrderHistoryItemsList().size() - 1).getStatus(), OrderStatus.DONE);
    }
*/
}
