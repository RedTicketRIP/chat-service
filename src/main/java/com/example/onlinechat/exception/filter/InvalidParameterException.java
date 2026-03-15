package com.example.onlinechat.exception.filter;

import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InvalidParameterException extends BasicException {
    public InvalidParameterException(String paramName, String expected, String val) {
        super(
                logMessage(paramName, expected, val),
                userMessage(paramName),
                HttpStatus.BAD_REQUEST,
                ErrorCode.INVALID_INPUT
        );
    }


    private static String[] logMessage(String paramName, String expected, String val) {
        StringBuilder builder = new StringBuilder();

        paramName = Objects.toString(paramName, "");
        expected = Objects.toString(expected, "");
        val = Objects.toString(val, "");

        builder.append("InvalidParameter:")
                .append("paramName=").append(paramName).append(", ")
                .append("expected=").append(expected).append(", ")
                .append("value=").append(val);

        return new String[]{builder.toString()};
    }

    private static String[] userMessage(String paramName) {
        StringBuilder builder = new StringBuilder();

        paramName = Objects.toString(paramName, "");

        builder.append("Invalid Parameter: ").append(paramName);

        return new String[]{builder.toString()};

    }

}
