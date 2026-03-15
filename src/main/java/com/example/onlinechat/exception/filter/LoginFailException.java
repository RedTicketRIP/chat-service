package com.example.onlinechat.exception.filter;

import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class LoginFailException extends BasicException {
    public LoginFailException() {
        super(
                new String[]{},
                new String[]{"login failed"},
                HttpStatus.UNAUTHORIZED,
                ErrorCode.LOGIN_FAIL
                );
    }

}
