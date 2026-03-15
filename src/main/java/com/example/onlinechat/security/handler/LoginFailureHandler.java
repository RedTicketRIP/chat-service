package com.example.onlinechat.security.handler;

import com.example.onlinechat.exception.filter.InvalidParameterException;
import com.example.onlinechat.exception.filter.LoginFailException;
import com.example.onlinechat.service.FilterExceptionService;
import com.example.onlinechat.service.LoginFailureCountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private final LoginFailureCountService loginFailureCountService;
    private final FilterExceptionService filterExceptionService;

    private void authenticationFailure(HttpServletRequest request) {
        String username = request.getParameter("username");
        if (username == null || username.isEmpty()) {
            throw new InvalidParameterException("username", "[not empty]", "[empty]");
        }

        loginFailureCountService.increaseFailureCount(username);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        try {
            authenticationFailure(request);

        } catch (InvalidParameterException invalidParameterException) {
            filterExceptionService.responseError(invalidParameterException, response);
        }

        filterExceptionService.responseError(new LoginFailException(), response);

    }

}
