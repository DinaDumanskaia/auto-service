package ru.itentica.autoservice;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.itentica.autoservice.repository.PrincipalRepository;
import ru.itentica.autoservice.repository.PrincipalRepositoryImpl;
import ru.itentica.autoservice.services.JDBCPrincipalServiceImpl;
import ru.itentica.autoservice.services.OrderService;
import ru.itentica.autoservice.services.OrderServiceImpl;
import ru.itentica.autoservice.services.PrincipalService;

import javax.sql.DataSource;

@TestConfiguration
class TestConfigurationTest {

    @Autowired
    DataSource dataSource;

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }
//    @Bean
//    public PrincipalRepository getPrincipalRepository() {
//        return new PrincipalRepositoryImpl(new JdbcTemplate());
//    }

//    @Bean
//    public OrderService getOrderService() {
//        return new OrderServiceImpl();
//    }

//    @Bean
//    public PrincipalService getPrincipalService() {
//        return new JDBCPrincipalServiceImpl(getPrincipalRepository());
//    }
}