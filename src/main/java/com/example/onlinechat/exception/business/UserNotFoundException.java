package com.example.onlinechat.exception.business;

import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Map;


public class UserNotFoundException extends BasicException {
    public UserNotFoundException(String username) {
        this(username, null);
    }

    public UserNotFoundException(String username, Exception e) {
        super(logMessage(username),
                userMessage(username),
                HttpStatus.NOT_FOUND,
                ErrorCode.RESOURCE_NOT_FOUND,
                extra());
    }

    private static String[] logMessage(String username) {
        String res = username + " not found.";

        return new String[]{ res };
    }

    private static String[] userMessage(String username) {
        return new String[]{"user not found"};
    }

    private static Map<String, Object> extra() {
        return null;
    }
}
