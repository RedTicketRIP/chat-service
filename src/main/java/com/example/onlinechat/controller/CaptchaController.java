package com.example.onlinechat.controller;

import com.example.onlinechat.domain.Captcha;
import com.example.onlinechat.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CaptchaController {
    private final CaptchaService captchaService;

    @GetMapping("/captcha")
    public ResponseEntity<byte[]> captcha(@RequestParam("username") String username) {
        Captcha captcha = captchaService.getCaptcha(username);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(captcha.getImage());
    }
}
