package ru.itentica.autoservice.services;

import ru.itentica.autoservice.entities.*;

import java.util.Collection;
import java.util.List;

public interface PrincipalService {
    Principal getClient(Long id) throws Throwable;
    Principal getAdministrator(Long id) throws Throwable;
    Principal getWorker(Long id) throws Throwable;

    Principal getClientWithoutId(String name, String phoneNumber, String address);

    Collection<Principal> getAllPrincipals();

    void saveInitializedPrincipal(Principal client);

    List<Principal> getPrincipals(Position administrator);
    List<Principal> getWorkerPrincipals();

    Principal getClientByName(String name);

    Principal getAdministratorByName(String name);

    Principal getWorkerByName(String name);
}
