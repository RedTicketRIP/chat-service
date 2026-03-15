package com.example.onlinechat.repository.captcha.interf;

import com.example.onlinechat.domain.Captcha;

public interface CaptchaRepository {
    public void save(String username, Captcha captcha);

    public Captcha get(String username);
}
