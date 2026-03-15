package com.example.onlinechat.dto.validation.anno;

import com.example.onlinechat.dto.validation.NoWhiteSpaceValidator;
import jakarta.validation.Constraint;
import org.springframework.messaging.handler.annotation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Constraint(validatedBy = NoWhiteSpaceValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoWhiteSpace {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
