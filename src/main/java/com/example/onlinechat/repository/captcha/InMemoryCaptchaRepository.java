package com.example.onlinechat.repository.captcha;

import com.example.onlinechat.domain.Captcha;
import com.example.onlinechat.repository.captcha.interf.CaptchaRepository;
import org.springframework.stereotype.Repository;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryCaptchaRepository implements CaptchaRepository {
    private Map<String, Captcha> captchaMap = new ConcurrentHashMap<>();

    @Override
    public void save(String username, Captcha captcha) {
        Captcha oldCaptcha = captchaMap.get(username);
        if (oldCaptcha != null) {
            throw new KeyAlreadyExistsException();
        }

        captchaMap.put(username, captcha);
    }

    @Override
    public Captcha get(String username) {
        Captcha captcha = captchaMap.get(username);
        if (captcha==null) {
            throw new NoSuchElementException();
        }

        return captcha;
    }
}
