package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    public static final int USER_ID_ONE = 1;
    public static final int USER_ID_TWO = 2;
    public static final int ADMIN_ID = 3;

    {
        save(new User(1,"User1","user1@gmail.com", "user1", Role.USER));
        save(new User(2,"User2","use21@gmail.com", "user2", Role.USER));
        save(new User(3,"Admin","admin@gmail.com", "admin", Role.ADMIN));
    }

    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        return user.getId() == null ? null : repository.put(user.getId(), user);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public Collection <User> getAll() {
        log.info("getAll");
        return repository.values();
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return getAll().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }
}
