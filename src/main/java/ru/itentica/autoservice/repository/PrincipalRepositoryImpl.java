package ru.itentica.autoservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itentica.autoservice.entities.Principal;

import java.util.List;
@Repository
public class PrincipalRepositoryImpl implements PrincipalRepository {

    private final JdbcTemplate jdbcTemplate;

    public PrincipalRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @Transactional
    public int save(Principal principal) {

        return jdbcTemplate.update("INSERT INTO principal (id, name, phone_number, position, birth_date, address) VALUES(?,?,?,?,?,?)",
                new Object[] {principal.getId(), principal.getName(), principal.getPhoneNumber(), principal.getPosition().name(), principal.getBirthDate(), principal.getAddress()});
    }

    @Override
    @Transactional
    public Principal findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM principal WHERE id=?",
                BeanPropertyRowMapper.newInstance(Principal.class), id);
    }

    @Override
    @Transactional
    public List<Principal> findAll() {
        return jdbcTemplate.query("SELECT * from principal", BeanPropertyRowMapper.newInstance(Principal.class));
    }

    @Override
    @Transactional
    public void deleteAll() {
        jdbcTemplate.update("delete from principal");
    }
}
