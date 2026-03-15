package com.example.onlinechat.security.filter.captcha;

import com.example.onlinechat.exception.filter.InvalidParameterException;
import com.example.onlinechat.exception.filter.RequireCaptchaAuthException;
import com.example.onlinechat.service.CaptchaService;
import com.example.onlinechat.service.FilterExceptionService;
import com.example.onlinechat.service.LoginFailureCountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CaptchaPolicyFilter extends OncePerRequestFilter {
    private final LoginFailureCountService loginFailureCountService;
    private final CaptchaService captchaService;
    private final FilterExceptionService filterExceptionService;
    private final String LOGIN_URL = "/login";
    private final String ALLOW_METHOD = "POST";
    private final long CAPTCHA_REQUIRE = 5;


    public void checkRequireCaptchaAuthentication(String username) {
        long cnt = loginFailureCountService.getFailureCount(username);
        if (cnt >= CAPTCHA_REQUIRE) {
            throw new RequireCaptchaAuthException(username, cnt);
        }
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        String method = request.getMethod();

        if (url.startsWith(LOGIN_URL) && method.equals(ALLOW_METHOD)) {
            String username = request.getParameter("username");

            try {
                if (username == null || username.isEmpty()) {
                    throw new InvalidParameterException("username", "[not empty]", "[empty]");
                }

                checkRequireCaptchaAuthentication(username);

                filterChain.doFilter(request, response);
                return;

            } catch (RequireCaptchaAuthException requireCaptchaAuthentication) {
                captchaService.getOrCreateCaptcha(username);

                filterExceptionService.responseError(requireCaptchaAuthentication, response);
            } catch (InvalidParameterException invalidParameterException) {
                filterExceptionService.responseError(invalidParameterException, response);
            }

        } else {
            filterChain.doFilter(request, response);
        }
    }
}
