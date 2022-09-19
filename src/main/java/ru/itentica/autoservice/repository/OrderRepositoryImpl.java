package ru.itentica.autoservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.itentica.autoservice.entities.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository{

    public static final String selectWorkItemsQuery = "SELECT id, quantity, cost_value, price_item " +
            "FROM work_items " +
            "WHERE ref_petition_id=?";

    public static final String selectOrderHistoryItemsQuery = "SELECT id, status, comment, creation_date " +
            "FROM order_history_items " +
            "WHERE ref_petition_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Override
    @Transactional
    public void save(Order order) {
        Principal administrator = order.getAdministrator();
        Principal master = order.getMaster();
        Principal client = order.getClient();
        //сохраняем трёх принципиалов и берём их id

        DefaultTransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus status = platformTransactionManager.getTransaction(paramTransactionDefinition);

        jdbcTemplate.update("INSERT INTO petition " +
                        "(id, reason, begin_date, end_date, comment_value, principals_client, principals_master, principals_admin) " +
                        "VALUES(?,?,?,?,?,?,?,?)",
                order.getId(),
                order.getReason(),
                order.getBeginDate(),
                order.getEndDate(),
                order.getComment(),
                client.getId(),
                getCheckedId(master),
                getCheckedId(administrator));
        platformTransactionManager.commit(status);
        status = platformTransactionManager.getTransaction(paramTransactionDefinition);

        saveHistory(order);
        saveWorkItems(order);
        platformTransactionManager.commit(status);
    }

    private Long getCheckedId(Principal principal) {
        if (principal == null) {
            return null;
        }
        return principal.getId();
    }

    private void saveWorkItems(Order order) {
        List<WorkItem> workItemsList = order.getWorkItems();
        jdbcTemplate.update("delete from work_items");
        jdbcTemplate.batchUpdate("INSERT INTO work_items (id, quantity, cost_value, price_item, ref_petition_id) VALUES (?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        WorkItem workItem = workItemsList.get(i);
                        System.out.println(workItem.toString());
                        ps.setLong(1, workItem.getId());
                        ps.setInt(2, workItem.getQuantity());
                        ps.setDouble(3, workItem.getCost());
                        /*ps.setLong(4, workItemsList.get(i).getPriceItem().getId());*/
                        ps.setString(4, workItem.getPriceItem().name());
                        ps.setLong(5, order.getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return workItemsList.size();
                    }
                });
    }

    private void saveHistory(Order order) {
        List<OrderHistoryItem> orderHistoryItemList = order.getOrderHistoryItemsList();
        // сохраняем ордер хистори элементы используя order id, взятый выше как ref_order_id
        jdbcTemplate.update("delete from order_history_items");
        jdbcTemplate.update("INSERT INTO order_history_items (id, status, comment, creation_date, ref_petition_id) VALUES(?,?,?,?,?)",
                orderHistoryItemList.get(0).getId(),
                orderHistoryItemList.get(0).getStatus().name()/*сохраняем статус как строку а не энам*/,
                orderHistoryItemList.get(0).getComment(),
                orderHistoryItemList.get(0).getCreationDate(),
                order.getId());
    }

    @Override
    @Transactional
    public void update(Order order) {
        // у нового версии ордера нужно взять все поля
        // все, что не коллекции обновить одним запросом
        // все, что коллекции - очистить и записать заново
        Long administratorId = order.getAdministrator().getId();
        Long masterId = order.getMaster().getId();
        Long clientId = order.getClient().getId();

        deleteOrderHistory(order.getId());
        saveHistory(order);

        deleteWorkItems(order.getId());
        saveWorkItems(order);

        // тоже самое что и для orderHistoryItemList

        jdbcTemplate.update("UPDATE petition " +
                        "SET " +
                        "reason = ?, " +
                        "begin_date = ?, " +
                        "end_date = ?, " +
                        "comment_value = ?, " +
                        "principals_client = ?, " +
                        "principals_master = ?, " +
                        "principals_admin = ? " +
                        "WHERE id = ?",
                order.getReason(),
                order.getBeginDate(),
                order.getEndDate(),
                order.getComment(),
                order.getClient(),
                order.getMaster(),
                order.getAdministrator(),
                order.getId());

    }

    private void deleteWorkItems(Long id) {
        jdbcTemplate.update("DELETE FROM work_items WHERE ref_petition_id = ?", id);

    }

    private void deleteOrderHistory(Long id) {
        jdbcTemplate.update("DELETE FROM order_history_items WHERE ref_petition_id = ?", id);
    }


    @Override
    @Transactional
    public Order findById(Long id) {
        List<WorkItem> workItems = getWorkItemList(id);
        List<OrderHistoryItem> historyItems = getOrderHistoryItemList(id);
        
        Order order = jdbcTemplate.queryForObject(
                "SELECT petition.id, petition.reason, petition.begin_date, petition.end_date, petition.comment_value, " +
                        "prls_c.id as client_id, prls_c.name as client_name, prls_c.phone_number as client_phone_number, prls_c.position as client_position, prls_c.birth_date as client_birth_date, prls_c.address as client_address, " +
                        "prls_m.id as master_id, prls_m.name as master_name, prls_m.phone_number as master_phone_number, prls_m.position as master_position, prls_m.birth_date as master_birth_date, prls_m.address as master_address, " +
                        "prls_a.id as admin_id, prls_a.name as admin_name, prls_a.phone_number as admin_phone_number, prls_a.position as admin_position, prls_a.birth_date as admin_birth_date, prls_a.address as admin_address " +
                    "FROM petition " +
                    "LEFT JOIN principal as prls_c ON prls_c.id = petition.principals_client " +
                    "LEFT JOIN principal as prls_m ON prls_m.id = petition.principals_master " +
                    "LEFT JOIN principal as prls_a ON prls_a.id = petition.principals_admin " +
                    "LEFT JOIN work_items as wi ON wi.ref_petition_id = petition.id " +
                    "WHERE petition.id=?;", new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

                long id = rs.getLong("id");
                String reason = rs.getString("reason");
                Date begin_date = rs.getDate("begin_date");
                Date end_date = rs.getDate("end_date");
                String comment = rs.getString("comment_value");

                Principal pClient = getPrincipalClient(rs);
                Principal pMaster = getPrincipalMaster(rs);
                Principal pAdmin = getPrincipalAdmin(rs);

                Order order1 = new Order(id, reason, begin_date, end_date, comment, workItems, historyItems, pClient, pMaster, pAdmin);
                return order1;
            }
        }, id);

        if (order != null) {
            List<WorkItem> workItemList = getWorkItemList(id);
            order.setWorkItems(workItemList);
        }
        return order;
    }

    private Principal getPrincipalAdmin(ResultSet rs) throws SQLException {
        String adminName = rs.getString("admin_name");
        if (adminName == null)
            return null;
        Long adminId = rs.getLong("admin_id");
        String adminPhoneNumber = rs.getString("admin_phone_number");
        Date adminBirthDate = rs.getDate("admin_birth_date");
        String adminPosition = rs.getString("admin_position");
        String adminAddress = rs.getString("admin_address");
        return new Principal(adminId, adminName, adminPhoneNumber, Position.valueOf(adminPosition), adminBirthDate, adminAddress);
    }

    private Principal getPrincipalMaster(ResultSet rs) throws SQLException {
        String masterName = rs.getString("master_name");
        if (masterName == null)
            return null;

        Long masterId = rs.getLong("master_id");
        String masterPhoneNumber = rs.getString("master_phone_number");
        Date masterBirthDate = rs.getDate("master_birth_date");
        String masterPosition = rs.getString("master_position");
        String masterAddress = rs.getString("master_address");
        return new Principal(masterId, masterName, masterPhoneNumber, Position.valueOf(masterPosition), masterBirthDate, masterAddress);
    }

    private Principal getPrincipalClient(ResultSet rs) throws SQLException {
        String clientName = rs.getString("client_name");
        if (clientName == null)
            return null;
        Long clientId = rs.getLong("client_id");
        String clientPhoneNumber = rs.getString("client_phone_number");
        Date clientBirthDate = rs.getDate("client_birth_date");
        String clientAddress = rs.getString("client_address");
        return new Principal(clientId, clientName, clientPhoneNumber, Position.CLIENT, clientBirthDate, clientAddress);
    }


    private List<OrderHistoryItem> getOrderHistoryItemList(long id) {
        return jdbcTemplate.query(
                selectOrderHistoryItemsQuery, new RowMapper<OrderHistoryItem>() {
                    @Override
                    public OrderHistoryItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                        OrderHistoryItem orderHistoryItem = new OrderHistoryItem();
                        long id = rs.getLong("id");
                        String status = rs.getString("status");
                        String comment = rs.getString("comment");
                        Date creationDate = rs.getDate("creation_date");

                        orderHistoryItem.setId(id);
                        orderHistoryItem.setStatus(OrderStatus.valueOf(status));
                        orderHistoryItem.setComment(comment);
                        orderHistoryItem.setCreationDate(creationDate);
                        return orderHistoryItem;
                    }
                }, id);
    }

    private List<WorkItem> getWorkItemList(Long id) {
        return jdbcTemplate.query(
                selectWorkItemsQuery, new RowMapper<WorkItem>() {
                    @Override
                    public WorkItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                        WorkItem workItem = new WorkItem();
                        long id = rs.getLong("id");
                        double cost = rs.getDouble("cost") ;
                        int quantity = rs.getInt("quantity");
                        String priceItemName = rs.getString("price_item");

                        workItem.setId(id);
                        workItem.setCost(cost);
                        workItem.setQuantity(quantity);
                        workItem.setPriceItem(PriceItem.valueOf(priceItemName));
                        return workItem;
                    }
                }, id);
    }

    @Override
    @Transactional
    public List<Order> findAll() {

        List<Order> orders = jdbcTemplate.query(
                "SELECT petition.id, petition.reason, petition.begin_date, petition.end_date, petition.comment_value, " +
                        "prls_c.id as client_id, prls_c.name as client_name, prls_c.phone_number as client_phone_number, prls_c.position as client_position, prls_c.birth_date as client_birth_date, prls_c.address as client_address, " +
                        "prls_m.id as master_id, prls_m.name as master_name, prls_m.phone_number as master_phone_number, prls_m.position as master_position, prls_m.birth_date as master_birth_date, prls_m.address as master_address, " +
                        "prls_a.id as admin_id, prls_a.name as admin_name, prls_a.phone_number as admin_phone_number, prls_a.position as admin_position, prls_a.birth_date as admin_birth_date, prls_a.address as admin_address " +
                        "FROM petition " +
                        "LEFT JOIN principal as prls_c ON prls_c.id = petition.principals_client " +
                        "LEFT JOIN principal as prls_m ON prls_m.id = petition.principals_master " +
                        "LEFT JOIN principal as prls_a ON prls_a.id = petition.principals_admin " +
                        "LEFT JOIN work_items as wi ON wi.ref_petition_id = petition.id", new RowMapper<Order>() {
                    @Override
                    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

                        long id = rs.getLong("id");
                        String reason = rs.getString("reason");
                        Date begin_date = rs.getDate("begin_date");
                        Date end_date = rs.getDate("end_date");
                        String comment = rs.getString("comment_value");

                        Principal pClient = getPrincipalClient(rs);
                        Principal pMaster = getPrincipalMaster(rs);
                        Principal pAdmin = getPrincipalAdmin(rs);

                        Order order1 = new Order(id, reason, begin_date, end_date, comment, Collections.emptyList(), Collections.emptyList(), pClient, pMaster, pAdmin);
                        return order1;
                    }
                });

        for(Order order : orders) {
            Long id = order.getId();
            List<WorkItem> workItems = getWorkItemList(id);
            List<OrderHistoryItem> historyItems = getOrderHistoryItemList(id);
            order.setWorkItems(workItems);
            order.setOrderHistoryItemsList(historyItems);
        }
        return orders;
    }

    @Override
    @Transactional
    public void deleteAll() {
        jdbcTemplate.update("delete from petition");
    }
}
