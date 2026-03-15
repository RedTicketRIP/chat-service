package com.example.onlinechat.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import groovy.util.logging.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class RootConfig {
    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }

    @Bean
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();

        properties.setProperty("kaptcha.border", "yes");

        // 테두리 색상 설정 (RGB 값으로 지정, 여기서는 연한 녹색)
        properties.setProperty("kaptcha.border.color", "105,179,90");

        // 캡차 텍스트의 글자 색상 (black, blue, red 등 색상명 또는 RGB 값)
        properties.setProperty("kaptcha.textproducer.font.color", "black");

        // 캡차 문자 간 간격 픽셀 수 (숫자가 클수록 문자 사이 간격이 넓어짐)
        properties.setProperty("kaptcha.textproducer.char.space", "5");

        // 캡차 문자열의 길이 (생성될 캡차 문자 개수)
        properties.setProperty("kaptcha.textproducer.char.length", "6");

        // 캡차 이미지의 너비 (픽셀 단위)
        properties.setProperty("kaptcha.image.width", "400");

        // 캡차 이미지의 높이 (픽셀 단위)
        properties.setProperty("kaptcha.image.height", "200");

        Config config = new Config(properties);
        kaptcha.setConfig(config);

        return kaptcha;
    }
}
