package com.example.onlinechat.exception;

import lombok.Data;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Map;

public class InvalidBindingResultException extends BasicException {
    public InvalidBindingResultException(BindingResult bindingResult) {
        super(logMessage(bindingResult),
                userMessage(bindingResult),
                HttpStatus.NOT_ACCEPTABLE,
                ErrorCode.INVALID_INPUT,
                extra(bindingResult));
    }

    private static Map<String, Object> extra(BindingResult bindingResult) {
        return Map.of("BindingResult", bindingResult);
    }

    private static String[] logMessage(BindingResult bindingResult) {
        return new String[]{};
    }

    private static String[] userMessage(BindingResult bindingResult) {
        List<ObjectError> errors = bindingResult.getAllErrors();

        return errors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);
    }
}
