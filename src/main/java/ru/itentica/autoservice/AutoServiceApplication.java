package ru.itentica.autoservice;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import ru.itentica.autoservice.entities.*;
import ru.itentica.autoservice.repository.OrderRepository;
import ru.itentica.autoservice.repository.PrincipalRepository;
import ru.itentica.autoservice.repository.PrincipalRepositoryImpl;
import ru.itentica.autoservice.services.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//@SpringBootApplication
public class AutoServiceApplication {
    private static final Logger log = LoggerFactory.getLogger(AutoServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AutoServiceApplication.class);
    }

/*    @Bean
    public CommandLineRunner demo1(PrincipalRepository repository) {
        return (args) -> {
            // save a few customers
            repository.deleteAll();
            Principal principal = new Principal(IdProvider.getNextLongId(), "Dina", "555-111", Position.CLIENT, new Date(), "EKB");
            repository.save(principal);
            repository.save(new Principal(IdProvider.getNextLongId(), "dEm", "555-112", Position.CLIENT, new Date(), "SPB"));
            repository.save(new Principal(IdProvider.getNextLongId(), "Anfisa", "555-222", Position.ADMINISTRATOR, new Date(), "MSK"));
            repository.save(new Principal(IdProvider.getNextLongId(), "Evgen", "555-111", Position.SLESAR, new Date(), "NOV"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Principal customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            Principal customer = repository.findById(1L);

            log.info("Customer found with findById(1L):");
            log.info("--------------------------------");
            log.info("" + customer);
            log.info("");

            log.info("");
        };
    }*/
/*    @Bean
    public CommandLineRunner demo2(OrderRepository repository) {
        return (args) -> {
            // save a few customers
            Long orderId = IdProvider.getNextLongId();
            WorkItem workItem = new WorkItem(1, PriceItem.OIL_CHANGE);
            OrderHistoryItem orderHistoryItem = new OrderHistoryItem(IdProvider.getNextLongId(), OrderStatus.NEW, "Заказ создан", new Date());
            Principal principal = new Principal(IdProvider.getNextLongId(), "Dina", "555-111", Position.CLIENT, new Date(), "EKB");

            System.out.println(">>>>> delete all");
            repository.deleteAll();
            System.out.println(">>>> next = " + getExistedElement(repository));
            System.out.println(">>>>> after delete all");
            repository.save(new Order(
                    orderId,
                    "Утечка топлива",
                    new Date(),
                    new Date(),
                    "comment",
                    Collections.singletonList(workItem),
                    Collections.singletonList(orderHistoryItem),
                    principal, principal, principal));
//            repository.save(new Order(IdProvider.getNextLongId(), "Вмятина на бампере", new Date(),
//                    null, null, Collections.emptyList(), null, null, null, null));
//            repository.save(new Order(IdProvider.getNextLongId(), "Поменять масло", new Date(),
//                    null, null, Collections.emptyList(), null, null, null, null));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Order order : repository.findAll()) {
                log.info(order.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            Order order = repository.findById(orderId);

            log.info("Customer found with findById(1L):");
            log.info("--------------------------------");
            log.info("" + order);
            log.info("");

            log.info("");
        };
    }*/

/*    private Order getExistedElement(OrderRepository repository) {
        List<Order> all = repository.findAll();
        return all!=null && !all.isEmpty() ? all.iterator().next() : null;
    }*/

//    @Bean
//    public IPrincipalProvider getPrincipalProvider() {
//        return new JDBCPrincipalProviderImpl();
//    }
/*
    @Bean
    public PrincipalRepository getPrincipalRepository() {
        return new PrincipalRepositoryImpl();
    }*/

/*
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
*/

    public DataSource getDataSource()
    {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");

        dataSource.setAutoCommitOnReturn(false);
        dataSource.setDefaultAutoCommit(false);
        dataSource.setUrl("jdbc:postgresql://localhost:5432/AutoService");
        dataSource.setUsername("postgres");
        dataSource.setPassword("Password");
        return dataSource;

    }


    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);

        return dataSourceTransactionManager;
    }
//    @Bean
//    public IOrderService getOrderService() {
//        return new OrderServiceImpl(getPrincipalProvider());
//    }
    // Transaction manager bean definition

}
