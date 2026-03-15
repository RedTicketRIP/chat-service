package com.example.onlinechat.controller.public_api;


import com.example.onlinechat.dto.UserDTO;
import com.example.onlinechat.exception.InvalidBindingResultException;
import com.example.onlinechat.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.MethodValidationInterceptor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    RequestResponseBodyMethodProcessor b;
    MethodValidationInterceptor a;
    @Operation(summary = "유저가입")
    @PostMapping("/")
    public Map<String,String> addUser(
             @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult)
    {
        Map<String, String> map = new HashMap<>();

        if (bindingResult.hasErrors()) {
            throw new InvalidBindingResultException(bindingResult);
        }

        userService.addUser(userDTO);

        map.put("uid", userDTO.getId());

        return map;
    }
}
