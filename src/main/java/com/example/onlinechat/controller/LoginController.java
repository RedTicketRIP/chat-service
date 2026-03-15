package com.example.onlinechat.controller;

import com.example.onlinechat.service.UserService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final DefaultKaptcha defaultKaptcha;

    @GetMapping("/")
    public String index() {
        return "index/index.html";
    }


    // view 를 위함.
    @GetMapping("/register")
    public void registerGet() {

    }

    @GetMapping("/login")
    public String loginGet() {
        return "/login/login.html";
    }

    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<byte[]> testGet() throws IOException {
        String text = defaultKaptcha.createText();

        BufferedImage image = defaultKaptcha.createImage("FFFFFFFFFFFFFFFFFf");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] imageBytes = baos.toByteArray();

        System.out.println(text);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
    }
    
}
