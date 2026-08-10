package com.gitlab.rideau7c2.backendlab.user.service;

import com.gitlab.rideau7c2.backendlab.exception.UserNotFoundException;
import com.gitlab.rideau7c2.backendlab.user.repository.User;
import com.gitlab.rideau7c2.backendlab.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
