package ru.itentica.autoservice.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itentica.autoservice.entities.*;
import ru.itentica.autoservice.repository.PrincipalRepositoryImpl;
import ru.itentica.autoservice.services.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// ограничение - у всех должны быть разные имена, чтобы работал поиск по имени
@Controller
@RequestMapping("/")
public class WebController {
    @Autowired
    private PrincipalService principalProvider;/* = new JDBCPrincipalServiceImpl(new PrincipalRepositoryImpl(new JdbcTemplate()))*/; // change to autowire

    @Autowired
    private OrderService orderService;/* = new OrderServiceImpl();*/

    /*private OrderService getOService() {
        return new OrderService() {
            @Override
            public Order orderRegistration(String login, Order order, String comment) {
                return null;
            }

            @Override
            public Order moveOrderToWork(Long orderId, Long masterId) throws Throwable {
                return null;
            }

            @Override
            public Order updateOrderStatus(Long orderId, OrderStatus orderStatus, String comment) throws Throwable {
                return null;
            }

            @Override
            public OrderStatus getOrderStatus(Long orderId) {
                return null;
            }

            @Override
            public Order createInitialOrder(String reason, String comment, Principal client) {
                return null;
            }

            @Override
            public Order getOrder(Long orderId) throws Throwable {
                return null;
            }

            @Override
            public List<Order> getAllOrders() {
                return null;
            }

            @Override
            public void updateOrder(Order order) throws Exception {

            }
        };
    }*/

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String startPage(@ModelAttribute("model") ModelMap model) {
        return "index";
    }

    @RequestMapping(value ="/principals", method = RequestMethod.GET)
    public String principalView(@ModelAttribute("model") ModelMap model) {
        model.addAttribute("principalCollection", principalProvider.getAllPrincipals());
        return "principals";
    }

    @RequestMapping(value = "/addPrincipal", method = RequestMethod.POST)
    public String addPrincipal(@ModelAttribute("model") ModelMap model, @RequestParam Map<String,String> allParams) {
        savePrincipal(allParams);
        model.addAttribute("principalCollection", principalProvider.getAllPrincipals());
        return "principals";
    }

    @RequestMapping(value = "/order_registration", method = RequestMethod.GET)
    public String orderRegistration(@ModelAttribute("model") ModelMap model) {
        populateModelByClientsAndAdmins(model);

        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("orders", orderList);

        return "order_registration";
    }

    @RequestMapping(value = "/register_order", method = RequestMethod.POST)
    public String registerOrder(@ModelAttribute("model") ModelMap model, @RequestParam Map<String,String> allParams) {
        populateModelByClientsAndAdmins(model);
        saveOrder(allParams);
        model.addAttribute("orders", orderService.getAllOrders());
        return "order_registration";
    }


    @RequestMapping(value = "/move_order_to_work", method = RequestMethod.GET)
    public String moveOrderToWork(@ModelAttribute("model") ModelMap model) {
        populateModelByWorkers(model);

        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("ordersCollection", orderList);

        return "move_order_to_work";
    }

    @RequestMapping(value = "/assign_worker", method = RequestMethod.POST)
    public String assignWorker(@ModelAttribute("model") ModelMap model, @RequestParam Map<String,String> allParams) throws Throwable {
        populateModelByWorkers(model);
        assignWorkerToOrder(allParams);

        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("ordersCollection", orderList);
        return "move_order_to_work";
    }

    @RequestMapping(value = "/add_work_items_to_order", method = RequestMethod.GET)
    public String addWorkItemsToOrder(@ModelAttribute("model") ModelMap model) {
        populateModelByPriceItems(model);

        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("ordersCollection", orderList);

        return "add_work_items_to_order";
    }

    @RequestMapping(value = "/assign_work_item_to_order", method = RequestMethod.POST)
    public String assignWorkItem(@ModelAttribute("model") ModelMap model, @RequestParam Map<String,String> allParams) throws Throwable {
        populateModelByPriceItems(model);
        addWorkItemToOrder(allParams);

        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("ordersCollection", orderList);
        return "add_work_items_to_order";
    }


    @RequestMapping(value = "/change_order_status", method = RequestMethod.GET)
    public String changeOrderStatus(@ModelAttribute("model") ModelMap model) {
        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("ordersCollection", orderList);

        return "change_order_status";
    }

    @RequestMapping(value = "/assign_order_status", method = RequestMethod.POST)
    public String assignOrderStatus(@ModelAttribute("model") ModelMap model, @RequestParam Map<String,String> allParams) throws Throwable {
        assignStatusToOrder(allParams);

        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("ordersCollection", orderList);
        return "change_order_status";
    }

    @RequestMapping(value = "/view_order_status", method = RequestMethod.GET)
    public String getOrderStatus(@ModelAttribute("model") ModelMap model) {
        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("ordersCollection", orderList);
        model.addAttribute("requestedOrder", null);
        return "view_order_status";
    }

    @RequestMapping(value = "/get_order_status", method = RequestMethod.POST)
    public String requestOrderStatus(@ModelAttribute("model") ModelMap model, @RequestParam Map<String,String> allParams) throws Throwable {
        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("ordersCollection", orderList);
        addRequestedOrderToModel(model, allParams);
        return "view_order_status";
    }

    @RequestMapping(value = "/update_order_info", method = RequestMethod.GET)
    public String getOrderInfoInitedForm(@ModelAttribute("model") ModelMap model) {
        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("ordersCollection", orderList);

        populateModelByClientsAndAdmins(model);
        populateModelByWorkers(model);

        model.addAttribute("requestedOrder", null);
        return "update_order_info";
    }


    @RequestMapping(value = "/get_order_current_info", method = RequestMethod.POST)
    public String getOrderCurrentInfo(@ModelAttribute("model") ModelMap model, @RequestParam Map<String,String> allParams) throws Throwable {
        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("ordersCollection", orderList);

        populateModelByClientsAndAdmins(model);
        populateModelByWorkers(model);

        addRequestedOrderToModel(model, allParams);

        return "update_order_info";
    }

    @RequestMapping(value = "/do_update_order", method = RequestMethod.POST)
    public String updateOrder(@ModelAttribute("model") ModelMap model, @RequestParam Map<String,String> allParams) throws Throwable {
        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("ordersCollection", orderList);

        populateModelByClientsAndAdmins(model);
        populateModelByWorkers(model);

        updateOrderDetails(allParams);

        addRequestedOrderToModel(model, allParams);

        return "update_order_info";
    }

    @RequestMapping(value = "/testExceptionHandler", method = RequestMethod.GET)
    public String testExceptionHandler() throws Exception {
        throw new Exception("BusinessException in testExceptionHandler");
    }


    //------------------------

    private void updateOrderDetails(Map<String, String> paramMap) throws Throwable {
        Long orderId = Long.parseLong(paramMap.get("order_id"));
        String adminName = paramMap.get("admin");
        String clientName = paramMap.get("client");
        String comment = paramMap.get("comment");
        String reason = paramMap.get("reason");
        String workerName = paramMap.get("worker");
        Date beginDate = parseDate(paramMap.get("begin_date"));
        Date endDate = parseDate(paramMap.get("end_date"));

        Principal client = principalProvider.getClientByName(clientName);
        Principal administrator = principalProvider.getAdministratorByName(adminName);
        Principal worker = principalProvider.getWorkerByName(workerName);

        Order order = orderService.getOrder(orderId);
        order.setAdministrator(administrator);
        order.setMaster(worker);
        order.setClient(client);
        order.setReason(reason);
        order.setComment(comment);
        order.setBeginDate(beginDate);
        order.setEndDate(endDate);

        orderService.updateOrder(order);
    }
    private void addRequestedOrderToModel(ModelMap model, Map<String, String> allParams) throws Throwable {
        Long orderId = Long.parseLong(allParams.get("order_id"));
        Order requestedOrder = orderService.getOrder(orderId);
        model.addAttribute("requestedOrder", requestedOrder);
    }
    // populate*** and other methods should work without allParams argument

    private void assignStatusToOrder(Map<String, String> allParams) throws Throwable {
        Long orderId = Long.parseLong(allParams.get("order_id"));
        OrderStatus status = OrderStatus.valueOf(allParams.get("status"));
        String comment = allParams.get("comment");

        orderService.updateOrderStatus(orderId, status, comment);
    }
    private void populateModelByPriceItems(ModelMap model) {
        List<PriceItem> priceItems = new ArrayList<>();
        priceItems.add(PriceItem.WHEEL_REPLACE);
        priceItems.add(PriceItem.OIL_CHANGE);
        priceItems.add(PriceItem.OIL_NUMBER_ONE);

        model.addAttribute("priceItems", priceItems);
    }

    private void addWorkItemToOrder(Map<String, String> allParams) throws Throwable {
        Long orderId = Long.parseLong(allParams.get("order_id"));
        int priceItemId = Integer.parseInt(allParams.get("price_item_id"));
        int quantity = Integer.parseInt(allParams.get("quantity"));

        Order order = orderService.getOrder(orderId);
        WorkItem workItem = new WorkItem(quantity, PriceItem.getById(priceItemId));
        order.addWorkItem(workItem);
        orderService.updateOrder(order);
    }
    private void assignWorkerToOrder(Map<String, String> allParams) throws Throwable {
        Long orderId = Long.parseLong(allParams.get("order_id"));
        Long workerId = Long.parseLong(allParams.get("worker_id"));

        orderService.moveOrderToWork(orderId, workerId);
    }

    private void populateModelByWorkers(ModelMap model) {
        model.addAttribute("workersList", new ArrayList<>(principalProvider.getWorkerPrincipals()));
    }

    private void populateModelByClientsAndAdmins(ModelMap model) {
        List<String> adminsList = principalProvider.getPrincipals(Position.ADMINISTRATOR)
                .stream()
                .map(Principal::getName)
                .collect(Collectors.toList());

        model.addAttribute("adminsList", adminsList);

        List<String> clientsList = principalProvider.getPrincipals(Position.CLIENT)
                .stream()
                .map(Principal::getName)
                .collect(Collectors.toList());

        model.addAttribute("clientsList", clientsList);
    }

    private void saveOrder(Map<String, String> paramMap) {
        String adminName = paramMap.get("admin");
        String clientName = paramMap.get("client");
        String comment = paramMap.get("comment");
        String reason = paramMap.get("reason");
        String clientNameManual = paramMap.get("client_name");
        Principal client;
        if (clientNameManual == null || clientNameManual.isBlank()) {
            client = principalProvider.getClientByName(clientName);
        } else {
            client = principalProvider.getClientWithoutId(clientNameManual, "", "");
        }
        Order initialOrder
                = orderService.createInitialOrder(reason, comment, client);
        initialOrder.setAdministrator(principalProvider.getAdministratorByName(adminName));

        orderService.orderRegistration(adminName, initialOrder, comment);
    }


    private void savePrincipal(Map<String, String> paramMap) {
        Long id = IdProvider.getNextLongId();
        String name = paramMap.get("name");
        String phone = paramMap.get("phoneNumber");
        Date bDay = parseDate(paramMap.get("birthDate"));
        Position position = parsePosition(paramMap.get("position"));
        String address = paramMap.get("address");

        principalProvider.saveInitializedPrincipal(new Principal(id, name, phone, position, bDay, address));
    }

    private Position parsePosition(String position) {
        Position retVal = Position.CLIENT;
        try {
            retVal = Position.valueOf(position);
        } catch (IllegalArgumentException ignored) {}

        return retVal;
    }

    private Date parseDate(String date) {
        try {
            return Date.from(LocalDate.parse(date).atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException exception) {
            return null;
        }
    }
}
