package com.example.onlinechat.service;

import com.example.onlinechat.domain.Captcha;
import com.example.onlinechat.repository.captcha.interf.CaptchaRepository;
import com.example.onlinechat.exception.filter.CaptchaNotFoundException;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CaptchaService {
    private final DefaultKaptcha defaultKaptcha;
    private final CaptchaRepository captchaRepository;

    public Captcha getOrCreateCaptcha(String username) {
        try {
            String text = defaultKaptcha.createText();
            BufferedImage image = defaultKaptcha.createImage(text);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(image, "jpg", baos);
            } catch (IOException ioException) {
                throw new RuntimeException("createCaptcha Failed");
            }

            Captcha captcha = Captcha.builder()
                    .text(text)
                    .image(baos.toByteArray())
                    .build();

            captchaRepository.save(username, captcha);

            return captcha;

        } catch (KeyAlreadyExistsException captchaAlreadyExist) {
            return captchaRepository.get(username);
        } catch (RuntimeException other) {
            throw other;
        }
    }

    public Captcha getCaptcha(String username) {
        try {
            return captchaRepository.get(username);

        } catch (NoSuchElementException noSuchElementException) {
            throw new CaptchaNotFoundException(username, noSuchElementException);
        } catch (RuntimeException other) {
            throw other;
        }
    }



}
