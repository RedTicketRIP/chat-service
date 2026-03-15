package com.example.onlinechat.exception.filter;

import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public class CaptchaNotFoundException extends BasicException {
    public CaptchaNotFoundException(String username, Exception e) {
        super(
                logMessage(username),
                userMessage(username),
                HttpStatus.NOT_FOUND,
                ErrorCode.RESOURCE_NOT_FOUND,
                e
        );
    }

    private static String[] logMessage(String username) {
        StringBuilder builder = new StringBuilder();

        username = Objects.toString(username, "");

        builder.append("CaptchaNotExist:")
                .append("username=").append(username).append(", ");

        return new String[]{builder.toString()};
    }

    private static String[] userMessage(String username) {
        StringBuilder builder = new StringBuilder();

        username = Objects.toString(username, "");

        return new String[]{builder.toString()};

    }
}
