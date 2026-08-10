package com.gitlab.rideau7c2.backendlab.user.repository;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    User save(User user);
    User delete(Long id);
}
