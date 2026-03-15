package com.example.onlinechat.security.filter.captcha;

import com.example.onlinechat.domain.Captcha;
import com.example.onlinechat.exception.UnexpectedException;
import com.example.onlinechat.exception.filter.InvalidParameterException;
import com.example.onlinechat.service.CaptchaService;
import com.example.onlinechat.exception.filter.CaptchaAuthenticationFailedException;
import com.example.onlinechat.service.FilterExceptionService;
import com.example.onlinechat.service.LoginFailureCountService;
import com.example.onlinechat.exception.filter.CaptchaNotFoundException;
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
public class CaptchaFilter extends OncePerRequestFilter {
    private final String CAPTCHA_URL = "/captcha";
    private final String ALLOW_METHOD = "POST";
    private final CaptchaService captchaService;
    private final FilterExceptionService filterExceptionService;
    private final LoginFailureCountService loginFailureCountService;

    private void authenticationCaptcha(String username, String text) {
        Captcha ans = captchaService.getCaptcha(username);
        if (text.equals(ans.getText())) {
            return;
        }

        throw new CaptchaAuthenticationFailedException(username, text, ans.getText());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        String method = request.getMethod();
        if (url.startsWith(CAPTCHA_URL) && method.equals(ALLOW_METHOD)) {
            try {
                String username = request.getParameter("username");
                if (username == null || username.isEmpty()) {
                    throw new InvalidParameterException("username", "[not empty]", "[empty]");
                }

                String captchaString = request.getParameter("captcha");
                if (captchaString == null || captchaString.isEmpty()) {
                    throw new InvalidParameterException("captchaString","[not empty]", "[empty]");
                }

                authenticationCaptcha(username, captchaString);

                loginFailureCountService.clearFailureCount(username);

            } catch (CaptchaAuthenticationFailedException
                     | CaptchaNotFoundException
                     | InvalidParameterException captchaAuthenticationFailed) {
                filterExceptionService.responseError(captchaAuthenticationFailed, response);
            } catch (RuntimeException unexpected) {
                filterExceptionService.responseError(new UnexpectedException(unexpected), response);
            }


        } else {
            filterChain.doFilter(request, response);
            return;
        }
    }
}
