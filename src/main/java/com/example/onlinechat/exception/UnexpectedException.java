package com.example.onlinechat.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class UnexpectedException extends BasicException {
    public UnexpectedException(Exception e) {
        super(
                logMessage(),
                userMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR,
                e
        );
    }

    private static String[] logMessage() {
        StringBuilder builder = new StringBuilder();

        builder.append("UnexpectedException occured, check!!!");

        return new String[]{builder.toString()};
    }

    private static String[] userMessage() {
        return new String[]{"INTERNAL_ERROR"};
    }


    private static Map<String, Object> getExtra(String a) {
        return null;
    }


}
