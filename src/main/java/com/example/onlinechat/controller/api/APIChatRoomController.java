package com.example.onlinechat.controller.api;

import com.example.onlinechat.dto.ChatDTO;
import com.example.onlinechat.dto.ChatRoomDTO;
import com.example.onlinechat.exception.business.ChatroomAlreadyJoinedException;
import com.example.onlinechat.exception.business.ChatroomNotFoundException;
import com.example.onlinechat.service.ChatRoomService;
import com.example.onlinechat.service.ChatService;
import com.example.onlinechat.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/chatroom")
@RequiredArgsConstructor
public class APIChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;
    private final NotificationService notificationService;

    @Operation(summary = "채팅방 정보 얻기.")
    @GetMapping("/{rid}")
    public ChatRoomDTO getChatroomInfo(
            @PathVariable("rid") String rid)
    {
        return chatRoomService.findById(rid);
    }

    @Operation(summary = "채팅방 생성.")
    @PostMapping("/{rid}")
    @Transactional
    public Map<String, String> createRoom(
            @PathVariable("rid") String rid,
            @RequestBody ChatRoomDTO chatRoomDTO)
    {
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> map = new HashMap<>();

        chatRoomDTO.setId(rid);

        chatRoomService.createChatRoom(chatRoomDTO, uid);
        chatRoomService.joinChatRoom(rid, uid);

        notificationService.notifyCreateRoom(rid);

        map.put("rid", rid);

        return map;
    }


    @Operation(summary = "채팅방 삭제.")
    @DeleteMapping("/{rid}")
    public Map<String, String> deleteRoom(
            @PathVariable("rid") String rid)
    {
        String chatRoomOwner = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> map = new HashMap<>();

        chatRoomService.deleteChatRoom(rid, chatRoomOwner);

        notificationService.notifyDeleteRoom(rid);


        map.put("rid", rid);

        return map;
    }

    @Operation(
            summary = "채팅방 탈퇴.",
            description = "채팅방이 없거나, 유저가 없거나, 유저가 채팅방에 속해있지 않거나. 3가지 경우 모두 하나의 결과로 반환됨.."
    )
    @DeleteMapping("/{rid}/member")
    public Map<String, String> leaveRoom(
            @PathVariable("rid") String rid)
    {
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> map = new HashMap<>();

        chatRoomService.leaveChatRoom(rid, uid);

        notificationService.notifyLeaveUser(rid, uid);

        map.put("rid", rid);

        return map;
    }

    @Operation(summary = "채팅방 가입.")
    @PostMapping("/{rid}/member")
    public List<ChatDTO> joinRoom(
            @PathVariable("rid") String rid)
    {
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> map = new HashMap<>();

        try {
            chatRoomService.joinChatRoom(rid, uid);
        } catch (ChatroomAlreadyJoinedException alreadyJoinedException) {
            return chatService.findChatHistory(rid, uid);
        }

        notificationService.notifyJoinUser(rid, uid);

        return chatService.findChatHistory(rid, uid);
    }

}
