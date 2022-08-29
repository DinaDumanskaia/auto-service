package ru.itentica.autoservice.repository;

import org.springframework.stereotype.Repository;
import ru.itentica.autoservice.entities.Principal;

import java.util.List;

public interface PrincipalRepository {
    int save(Principal principal);

    Principal findById(Long id);

    List<Principal> findAll();

    void deleteAll();
}
