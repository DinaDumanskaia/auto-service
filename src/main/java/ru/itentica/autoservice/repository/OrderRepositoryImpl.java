package ru.itentica.autoservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.itentica.autoservice.entities.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Override
    @Transactional
    public void save(Order order) {
        System.out.println(">>>>> start saving");
        Principal administrator = order.getAdministrator();
        Principal master = order.getMaster();
        Principal client = order.getClient();
        //сохраняем трёх принципиалов и берём их id

//        jdbcTemplate.update("INSERT INTO " + "principal" + "(id, name, phone_number, position, birth_date, address) VALUES(?,?,?,?,?,?)",
//                administrator.getId(), administrator.getName(), administrator.getPhoneNumber(), administrator.getPosition().name(), administrator.getBirthDate(), administrator.getAddress());
//        jdbcTemplate.update("INSERT INTO " + "principal" + "(id, name, phone_number, position, birth_date, address) VALUES(?,?,?,?,?,?)",
//                master.getId(), master.getName(), master.getPhoneNumber(), master.getPosition().name(), master.getBirthDate(), master.getAddress());
//        jdbcTemplate.update("INSERT INTO " + "principal" + "(id, name, phone_number, position, birth_date, address) VALUES(?,?,?,?,?,?)",
//                client.getId(), client.getName(), client.getPhoneNumber(), client.getPosition().name(), client.getBirthDate(), client.getAddress());

        DefaultTransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus status = platformTransactionManager.getTransaction(paramTransactionDefinition);


        System.out.println(">>>>>>>>>>>>>>> insert into order");
        jdbcTemplate.update("INSERT INTO \"order\" " +
                        "(id, reason, begin_date, end_date, comment_value, principals_client, principals_master, principals_admin) " +
                        "VALUES(?,?,?,?,?,?,?,?)",
                order.getId(),
                order.getReason(),
                order.getBeginDate(),
                order.getEndDate(),
                order.getComment(),
                client.getId(),
                master.getId(),
                administrator.getId());
        platformTransactionManager.commit(status);
        status = platformTransactionManager.getTransaction(paramTransactionDefinition);
        System.out.println(">>>>>>>>>>>>>>> after insert into order");


        System.out.println(">>>>> before save history");
        saveHistory(order);
        System.out.println(">>>>> after save history");
        System.out.println(">>>>> before save work items");
        saveWorkItems(order);
        System.out.println(">>>>> after save work items");
        platformTransactionManager.commit(status);
    }

    private void saveWorkItems(Order order) {
        List<WorkItem> workItemsList = order.getWorkItems();
        jdbcTemplate.update("delete from work_items");
        jdbcTemplate.batchUpdate("INSERT INTO work_items (id, quantity, cost_value, price_item, ref_order_id) VALUES (?, ?, ?, ?, ?)",
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
        jdbcTemplate.update("INSERT INTO order_history_items (id, status, comment, creation_date, ref_order_id) VALUES(?,?,?,?,?)",
                orderHistoryItemList.get(0).getId(),
                orderHistoryItemList.get(0).getStatus().name()/*сохраняем статус как строку а не энам*/,
                orderHistoryItemList.get(0).getComment(),
                orderHistoryItemList.get(0).getCreationDate(),
                order.getId());

                /*new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setBigDecimal(1, BigDecimal.valueOf(orderHistoryItemList.get(i).getId()));
                        ps.setString(2, orderHistoryItemList.get(i).getStatus().name());
                        ps.setString(3, orderHistoryItemList.get(i).getComment());
                        ps.setDate(4, new Date(orderHistoryItemList.get(i).getCreationDate().getTime()));
                        ps.setLong(5, order.getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return orderHistoryItemList.size();
                    }
                })*/;
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

        jdbcTemplate.update("UPDATE \"order\" " +
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
        jdbcTemplate.update("DELETE FROM work_items WHERE ref_order_id = ?", id);

    }

    private void deleteOrderHistory(Long id) {
        jdbcTemplate.update("DELETE FROM order_history_items WHERE ref_order_id = ?", id);
    }


    @Override
    @Transactional
    public Order findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM \"order\" WHERE id=?",
                BeanPropertyRowMapper.newInstance(Order.class), id);
    }

    @Override
    @Transactional
    public List<Order> findAll() {
        return jdbcTemplate.query("SELECT * from \"order\"", BeanPropertyRowMapper.newInstance(Order.class));
    }

    @Override
    @Transactional
    public void deleteAll() {
        jdbcTemplate.update("delete from \"order\"");
    }
}
