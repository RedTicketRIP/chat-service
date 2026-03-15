package com.example.onlinechat.repository.captcha.interf;

public interface LoginFailureCountRepository {
    public long increaseFailureCount(String username);
    public void clearFailureCount(String username);
    public long getFailureCount(String username);
}
