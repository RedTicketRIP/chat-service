package com.example.onlinechat.service;

import com.example.onlinechat.repository.captcha.interf.LoginFailureCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginFailureCountService {
    private final LoginFailureCountRepository loginFailureCountRepository;

    public long increaseFailureCount(String username) {
        return loginFailureCountRepository.increaseFailureCount(username);
    }

    public void clearFailureCount(String username) {
        loginFailureCountRepository.clearFailureCount(username);
    }

    public long getFailureCount(String username) {
        return loginFailureCountRepository.getFailureCount(username);
    }
}
