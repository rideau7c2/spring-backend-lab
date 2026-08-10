package com.gitlab.rideau7c2.backendlab.user.repository;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> users;
    private Long nextId;

    public InMemoryUserRepository() {
        users = new HashMap<>();
        nextId = 0L;
    }

    @Override
    public Optional<User> findById(Long id) {
        Objects.requireNonNull(id, "id must not be null");
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User save(User user) {
        Objects.requireNonNull(user, "user must not be null");
        if (user.id() != null) {
            if (users.containsKey(user.id())) {
                users.replace(user.id(), user);
                return user;
            } else {
                throw new NoSuchElementException("user with id:%d not exists".formatted(user.id()));
            }
        } else {
            User newUser = new User(nextId, user.name(), user.email());
            users.put(nextId, newUser);
            nextId++;
            return newUser;
        }
    }

    @Override
    public User delete(Long id) {
        return users.remove(id);
    }
}
