package com.example.onlinechat.domain;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Captcha {
    private byte[] image;
    private String text;
}
