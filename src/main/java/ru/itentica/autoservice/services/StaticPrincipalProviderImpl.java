package ru.itentica.autoservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ru.itentica.autoservice.entities.Principal;
import ru.itentica.autoservice.repository.PrincipalRepository;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Методы сервиса используют захардкоженные значения
 */
public class StaticPrincipalProviderImpl /*implements IPrincipalProvider*/ {
 /*   @Autowired
    PrincipalRepository principalRepository;
//    public static final int UNDEFINED_ID = -1;
    public static final Long UNDEFINED_ID = -1L;
    public static final int CLIENT_ID = IdProvider.getNextIntId();
    public static final int ADMIN_ID = IdProvider.getNextIntId();
    public static final int WORKER_ID = IdProvider.getNextIntId();

//    private static final Map<Integer, Principal> map = new HashMap<>();
//    static {
//        map.put(CLIENT_ID, new Principal(CLIENT_ID, "Ivan", "555-556", Position.CLIENT, new Date(System.currentTimeMillis() - 100000), "Pskov"));
//        map.put(ADMIN_ID, new Principal(ADMIN_ID, "Eva", "555-123", Position.ADMINISTRATOR, new Date(System.currentTimeMillis() - 200000), "Lvov"));
//        map.put(WORKER_ID, new Principal(WORKER_ID, "Stanislav", "555-256", Position.SLESAR, new Date(System.currentTimeMillis() - 400000), "Brest"));
//    }
// три следующих метода можно объединить в getPrincipal(int id);
    public Principal getPrincipal(int id) throws Throwable {
        return principalRepository.findById((long) id)
                .orElseThrow((Supplier<Throwable>) () -> new IllegalArgumentException("Incorrect Id"));
    }
    @Override
    public Principal getClient(int id) throws Throwable {
        Principal principal = getPrincipal(id);
        if (!principal.getPosition().equals(Position.CLIENT))
            throw new Exception("Principal has non-working position");
        return principal;
    }
    @Override
    public Principal getAdministrator(int id) throws Throwable {
        Principal principal = getPrincipal(id);
        if (!principal.getPosition().equals(Position.ADMINISTRATOR))
            throw new Exception("Principal has non-working position");
        return principal;
    }

    Set<Position> workersPositions = EnumSet.of(Position.SLESAR, Position.DIRECTOR, Position.STARSHIY_SMENI, Position.MASTER);

    @Override
    public Principal getWorker(int id) throws Throwable {
        Principal principal = getPrincipal(id);
        if (!workersPositions.contains(principal.getPosition()))
            throw new Exception("Principal has non-working position");
        return principal;
    }

    *//**
     * Вернёт клиента по описанию без id (метод для целей тестов)
     *//*
    @Override
    public Principal getClientWithoutId(String name, String phoneNumber, String address) {
        return new Principal(UNDEFINED_ID, name, phoneNumber, Position.CLIENT, null, address);
    }

    @Override
    @Transactional
    public Collection<Principal> getAllPrincipals() {
        Iterable<Principal> source = principalRepository.findAll();
        Collection<Principal> target = new ArrayList<>();
        source.forEach(target::add);
        return target;

        //return map.values();
    }

    @Override
    @Transactional
    public void saveInitializedPrincipal(Principal client) {
        principalRepository.save(client);
        //map.put(client.getId(), client);
    }

    @Override
    @Transactional
    public List<Principal> getPrincipals(Position position) {
        return getAllPrincipals().stream()
                .filter(principal -> principal.getPosition()
                .equals(position)).collect(Collectors.toList());
//        return map.values().stream()
//                .filter(principal -> principal.getPosition().equals(position))
//                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Principal> getWorkerPrincipals() {
        Set<Position> workers = Set.of(Position.SLESAR, Position.MASTER, Position.STARSHIY_SMENI, Position.DIRECTOR);
        return getAllPrincipals().stream()
                .filter(principal -> workers.contains(principal.getPosition()))
                .collect(Collectors.toList());
//        return map.values().stream()
//                .filter(principal -> workers.contains(principal.getPosition()))
//                .collect(Collectors.toList());
    }

    @Override
    public Principal getClientByName(String name) {
        return getPrincipals(Position.CLIENT)
                .stream()
                .filter(e -> e.getName().equals(name))
                .findAny()
                .orElseThrow();
    }

    @Override
    public Principal getAdministratorByName(String name) {
        return getPrincipals(Position.ADMINISTRATOR)
                .stream()
                .filter(e -> e.getName().equals(name))
                .findAny()
                .orElseThrow();
    }

    @Override
    public Principal getWorkerByName(String name) {
        return getWorkerPrincipals()
                .stream()
                .filter(e -> e.getName().equals(name))
                .findAny()
                .orElseThrow();
    }*/
}
