package com.example.onlinechat.repository.captcha;

import com.example.onlinechat.repository.captcha.interf.LoginFailureCountRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class InMemoryLoginFailureCountRepository implements LoginFailureCountRepository {
    // Map<Username, FailureCount>
    private Map<String, Integer> failureCount = new ConcurrentHashMap<>();
    @Override
    public long increaseFailureCount(String username) {
        return failureCount.merge(username, 1, Integer::sum);
    }

    @Override
    public void clearFailureCount(String username) {
        failureCount.remove(username);
    }

    @Override
    public long getFailureCount(String username) {
        return failureCount.getOrDefault(username, 0);
    }

}
