package com.example.onlinechat.exception.filter;

import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public class CaptchaAuthenticationFailedException extends BasicException {

    public CaptchaAuthenticationFailedException(String username, String val, String captchaString) {
        super(
                logMessage(username, val, captchaString),
                userMessage(),
                HttpStatus.UNAUTHORIZED,
                ErrorCode.ACCESS_DENIED
        );
    }

    private static String[] logMessage(String username, String val, String captchaString) {
        StringBuilder builder = new StringBuilder();

        username = Objects.toString(username, "");
        val = Objects.toString(val, "");
        captchaString = Objects.toString(captchaString, "");

        builder.append("CaptchaAuthenticationFailed:")
                .append("username=").append(username).append(", ")
                .append("val=").append(val).append(", ")
                .append("captchaString=").append(captchaString);

        return new String[]{builder.toString()};
    }

    private static String[] userMessage() {
        StringBuilder builder = new StringBuilder();

        builder.append("CaptchaAuthenticationFailed: ")
                .append("captcha authentication failed");

        return new String[]{builder.toString()};

    }

}
