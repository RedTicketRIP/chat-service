package com.example.onlinechat.exception.business;

import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Map;


public class UserAlreadyExistException extends BasicException {
    public UserAlreadyExistException(String username) {
        this(username, null);
    }

    public UserAlreadyExistException(String username, Exception e) {
        super(logMessage(username),
                userMessage(username),
                HttpStatus.NOT_ACCEPTABLE,
                ErrorCode.RESOURCE_NOT_FOUND,
                extra());
    }

    private static String[] logMessage(String username) {
        String res = username + " already exist.";

        return new String[]{ res };
    }

    private static String[] userMessage(String username) {
        return new String[]{"user already exist."};
    }

    private static Map<String, Object> extra() {
        return null;
    }
}
