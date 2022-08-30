package ru.itentica.autoservice.services;


import org.springframework.stereotype.Service;
import ru.itentica.autoservice.entities.*;
import ru.itentica.autoservice.repository.PrincipalRepository;

import java.util.*;
import java.util.stream.Collectors;

import static ru.itentica.autoservice.services.OrderServiceImpl.UNDEFINED_ID;

@Service
public class JDBCPrincipalServiceImpl implements PrincipalService {
//    @Autowired
    private PrincipalRepository principalRepository;

    public static final Long CLIENT_ID = IdProvider.getNextLongId();
    public static final Long ADMIN_ID = IdProvider.getNextLongId();
    public static final Long WORKER_ID = IdProvider.getNextLongId();

    public JDBCPrincipalServiceImpl(PrincipalRepository principalRepository) {
        this.principalRepository = principalRepository;
    }

    //        private static final Map<Long, Principal> map = new HashMap<>();
//    static {
//        map.put(CLIENT_ID, new Principal(CLIENT_ID, "Ivan", "555-556", Position.CLIENT, new Date(System.currentTimeMillis() - 100000), "Pskov"));
//        map.put(ADMIN_ID, new Principal(ADMIN_ID, "Eva", "555-123", Position.ADMINISTRATOR, new Date(System.currentTimeMillis() - 200000), "Lvov"));
//        map.put(WORKER_ID, new Principal(WORKER_ID, "Stanislav", "555-256", Position.SLESAR, new Date(System.currentTimeMillis() - 400000), "Brest"));
//    }
    @Override
    public Principal getClient(Long id) throws Throwable {
        Principal principal = principalRepository.findById(id);
        if (!principal.getPosition().equals(Position.CLIENT))
            throw new Exception("Principal has non-working position");
        return principal;
    }

    @Override
    public Principal getAdministrator(Long id) throws Throwable {
        Principal principal = principalRepository.findById(id);
        if (!principal.getPosition().equals(Position.ADMINISTRATOR))
            throw new Exception("Principal has non-working position");
        return principal;
    }

    Set<Position> workersPositions = EnumSet.of(Position.SLESAR, Position.DIRECTOR, Position.STARSHIY_SMENI, Position.MASTER);

    @Override
    public Principal getWorker(Long id) throws Throwable {
        Principal principal = principalRepository.findById(id);
        if (!workersPositions.contains(principal.getPosition()))
            throw new Exception("Principal has non-working position");
        return principal;
    }

    @Override
    public Principal getClientWithoutId(String name, String phoneNumber, String address) {
        return new Principal(UNDEFINED_ID, name, phoneNumber, Position.CLIENT, null, address);
    }

    @Override
    public Collection<Principal> getAllPrincipals() {
        Iterable<Principal> source = principalRepository.findAll();
        Collection<Principal> target = new ArrayList<>();
        source.forEach(target::add);
        return target;

        //return map.values();
    }

    @Override
    public void saveInitializedPrincipal(Principal principal) {
        principalRepository.save(principal);
    }

    @Override
    public List<Principal> getPrincipals(Position position) {
        return getAllPrincipals().stream()
                .filter(principal -> principal.getPosition()
                        .equals(position)).collect(Collectors.toList());
    }

    @Override
    public List<Principal> getWorkerPrincipals() {
        return getAllPrincipals().stream()
                .filter(principal -> workersPositions.contains(principal.getPosition()))
                .collect(Collectors.toList());
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
    }
}
