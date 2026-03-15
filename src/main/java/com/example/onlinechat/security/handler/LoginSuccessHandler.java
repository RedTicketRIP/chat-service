package com.example.onlinechat.security.handler;

import com.example.onlinechat.service.LoginFailureCountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final LoginFailureCountService loginFailureCountService;
    private final String MAIN_PAGE_URL = "/";

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        loginFailureCountService.clearFailureCount(authentication.getName());

        response.sendRedirect(MAIN_PAGE_URL);
    }
}
