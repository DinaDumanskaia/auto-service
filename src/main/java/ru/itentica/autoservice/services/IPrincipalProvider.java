package ru.itentica.autoservice.services;

import ru.itentica.autoservice.entities.*;

import java.util.Collection;
import java.util.List;

public interface IPrincipalProvider {
    Principal getClient(int id) throws Throwable;
    Principal getAdministrator(int id) throws Throwable;
    Principal getWorker(int id) throws Throwable;

    Principal getClientWithoutId(String name, String phoneNumber, String address);

    Collection<Principal> getAllPrincipals();

    void saveInitializedPrincipal(Principal client);

    List<Principal> getPrincipals(Position administrator);
    List<Principal> getWorkerPrincipals();

    Principal getClientByName(String name);

    Principal getAdministratorByName(String name);

    Principal getWorkerByName(String name);
}
