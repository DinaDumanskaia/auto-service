package ru.itentica.autoservice.services;


import ru.itentica.autoservice.entities.*;

import java.util.Collection;
import java.util.List;

public class JDBCPrincipalProviderImpl implements IPrincipalProvider {

    @Override
    public Principal getClient(int id) throws Throwable {
        return null;
    }

    @Override
    public Principal getAdministrator(int id) throws Throwable {
        return null;
    }

    @Override
    public Principal getWorker(int id) throws Throwable {
        return null;
    }

    @Override
    public Principal getClientWithoutId(String name, String phoneNumber, String address) {
        return null;
    }

    @Override
    public Collection<Principal> getAllPrincipals() {
        return null;
    }

    @Override
    public void saveInitializedPrincipal(Principal client) {

    }

    @Override
    public List<Principal> getPrincipals(Position administrator) {
        return null;
    }

    @Override
    public List<Principal> getWorkerPrincipals() {
        return null;
    }

    @Override
    public Principal getClientByName(String name) {
        return null;
    }

    @Override
    public Principal getAdministratorByName(String name) {
        return null;
    }

    @Override
    public Principal getWorkerByName(String name) {
        return null;
    }
}
