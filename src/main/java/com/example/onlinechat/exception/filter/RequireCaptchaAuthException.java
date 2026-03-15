package com.example.onlinechat.exception.filter;

import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public class RequireCaptchaAuthException extends BasicException {
    public RequireCaptchaAuthException(String username, long loginFailureCount) {
        super(
                logMessage(username, loginFailureCount),
                userMessage(),
                HttpStatus.UNAUTHORIZED,
                ErrorCode.CAPTCHA_REQUIRED
        );
    }


    private static String[] logMessage(String username, long loginFailureCount) {
        StringBuilder builder = new StringBuilder();

        username = Objects.toString(username, "");


        builder.append("RequireCaptchaAuth:")
                .append("username=").append(username).append(", ")
                .append("loginFailureCount=").append(loginFailureCount);

        return new String[]{builder.toString()};
    }

    private static String[] userMessage() {
        StringBuilder builder = new StringBuilder();


        builder.append("require captcha authentication.");

        return new String[]{builder.toString()};

    }

}

