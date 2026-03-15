package com.example.onlinechat.config;

import com.example.onlinechat.security.CustomUserDetailsService;
import com.example.onlinechat.security.filter.captcha.CaptchaFilter;
import com.example.onlinechat.security.filter.captcha.CaptchaPolicyFilter;
import com.example.onlinechat.security.handler.LoginFailureHandler;
import com.example.onlinechat.security.handler.LoginSuccessHandler;
import com.example.onlinechat.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private UserService userService;
    private CaptchaPolicyFilter captchaPolicyFilter;
    private CaptchaFilter captchaFilter;
    private LoginSuccessHandler loginSuccessHandler;
    private LoginFailureHandler loginFailureHandler;

    @Lazy
    public SecurityConfig(UserService userService,
                          CaptchaPolicyFilter captchaPolicyFilter,
                          CaptchaFilter captchaFilter,
                          LoginFailureHandler loginFailureHandler,
                          LoginSuccessHandler loginSuccessHandler) {
        this.userService = userService;
        this.captchaPolicyFilter = captchaPolicyFilter;
        this.captchaFilter = captchaFilter;
        this.loginFailureHandler = loginFailureHandler;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public DefaultSecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.formLogin(form -> form
                .loginPage("/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
        );

        http
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(captchaPolicyFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(request -> {
            request.anyRequest().permitAll();
        });

        DefaultSecurityFilterChain securityFilterChain = http.build();
        securityFilterChain.getFilters().forEach(i -> System.out.println(i));

        return securityFilterChain;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        CustomUserDetailsService userDetailsService = new CustomUserDetailsService();

        userDetailsService.setEncoder(passwordEncoder());
        userDetailsService.setUserService(userService);

        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
