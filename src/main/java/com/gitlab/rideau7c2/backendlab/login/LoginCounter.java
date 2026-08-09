package com.gitlab.rideau7c2.backendlab.login;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginCounter {
    private final Map<Long, Integer> counters = new ConcurrentHashMap<>();

    public int registerLogin(LoginEvent event) {
        Objects.requireNonNull(event, "event must not be null");
        return counters.merge(event.userId(), 1, Integer::sum);
    }

    public int getLoginCount(Long userId) {
        Objects.requireNonNull(userId, "userId must not be null");
        return counters.getOrDefault(userId, 0);
    }
}

