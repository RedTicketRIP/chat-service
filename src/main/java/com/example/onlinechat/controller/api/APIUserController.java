package com.example.onlinechat.controller.api;

import com.example.onlinechat.dto.ChatRoomDTO;
import com.example.onlinechat.dto.UserSummaryDTO;
import com.example.onlinechat.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class APIUserController {
    private final UserService userService;


    @Operation(summary = "내 정보 반환.")
    @GetMapping("/")
    public UserSummaryDTO getUserProfile() {
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();

        return userService.findSummaryById(uid);
    }


    @Operation(summary = "특정유저가 가입한 모든 채팅방 반환.")
    @GetMapping("/chatroom")
    public List<ChatRoomDTO> getUserParticipating()
    {
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();

        return userService.getUserParticipating(uid);
    }


    @Operation(summary = "회원탈퇴.")
    @DeleteMapping("/")
    public void unregister() {

    }

    @PostMapping("/logout")
    public void logout() {
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();

        userService.updateIsOnline(uid, false);
    }
}
