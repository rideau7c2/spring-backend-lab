package com.gitlab.rideau7c2.backendlab.user;

public interface UserRepository {
    User findById(Long id);
    User save(User user);
    User delete(Long id);
}
