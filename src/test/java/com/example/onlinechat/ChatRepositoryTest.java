package com.example.onlinechat;

import com.example.onlinechat.domain.Chat;
import com.example.onlinechat.domain.ChatRoom;
import com.example.onlinechat.dto.ChatDTO;
import com.example.onlinechat.dto.ChatRoomDTO;
import com.example.onlinechat.dto.UserDTO;
import com.example.onlinechat.repository.ChatRepository;
import com.example.onlinechat.repository.ChatRoomRepository;
import com.example.onlinechat.repository.UserRepository;
import com.example.onlinechat.service.ChatRoomService;
import com.example.onlinechat.service.ChatService;
import com.example.onlinechat.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class ChatRepositoryTest {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    UserService userService;

    @Autowired
    ChatService chatService;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    List<ChatRoomDTO> chatRoomDTOList = null;
    List<UserDTO> userDTOList = null;

    public void clearAllTable() {
        chatRoomRepository.deleteAll();
        userRepository.deleteAll();
        chatRepository.deleteAll();
    }

    @BeforeEach
    public void init() {
        clearAllTable();

        chatRoomDTOList = IntStream.rangeClosed(1, 10).mapToObj(i -> {
            ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()
                    .id("chatRoom"+i)
                    .build();

            chatRoomService.createChatRoom(chatRoomDTO, "owner1");

            return chatRoomDTO;
        }).toList();

        userDTOList = IntStream.rangeClosed(1, 10).mapToObj(i -> {
            UserDTO userDTO = UserDTO.builder()
                    .id("user"+i)
                    .password("password"+i)
                    .build();

            userService.addUser(userDTO);

            return userDTO;
        }).toList();
    }

    @Test
    public void findChatHistoryTest() {
        ChatRoomDTO chatRoomDTO = chatRoomDTOList.get(0);
        UserDTO userDTO = userDTOList.get(0);

        // 유저 chatroom 참가.
        chatRoomService.joinChatRoom(chatRoomDTO.getId(), userDTO.getId());

        // 참가한 chatroom 에 댓글 5개 추가.
        for (int i=0; i<5; i++) {
            ChatDTO chatDTO = ChatDTO.builder()
                    .room_id(chatRoomDTO.getId())
                    .user_id(userDTO.getId())
                    .sendTime(LocalDateTime.now())
                    .content("testChat" + i)
                    .build();

            chatService.storeChat(chatDTO);
        }

        // 댓글 불러오기 확인.
        List<Chat> chatList = chatRepository.findChatHistory(chatRoomDTO.getId(), userDTO.getId());
        assertThat(chatList.size(),equalTo(5));

        // 다른 방 참가
        chatRoomService.leaveChatRoom(chatRoomDTO.getId(), userDTO.getId());

        ChatRoomDTO newChatRoomDTO = chatRoomDTOList.get(1);
        chatRoomService.joinChatRoom(newChatRoomDTO.getId(), userDTO.getId());

        chatList = chatRepository.findChatHistory(chatRoomDTO.getId(), userDTO.getId());
        assertThat(chatList.size(),equalTo(0));


    }

}
