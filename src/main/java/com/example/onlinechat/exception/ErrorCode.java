package com.example.onlinechat.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ACCESS_DENIED("ACCESS_DENIED"),
    CAPTCHA_REQUIRED("CAPTCHA_REQUIRED"),
    INVALID_INPUT("INVALID_INPUT"),
    INTERNAL_ERROR("INTERNAL_ERROR"),
    LOGIN_FAIL("LOGIN_FAIL"),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND"),
    RESOURCE_ALREADY_EXIST("RESOURCE_ALREADY_EXIST"),
    FORBIDDEN_ACTION("FORBIDDEN_ACTION");


    private final String errString;

    ErrorCode(String errString) {
        this.errString = errString;
    }
}
