package com.example.onlinechat;

import com.example.onlinechat.dto.ChatRoomDTO;
import com.example.onlinechat.dto.UserDTO;
import com.example.onlinechat.exception.business.ChatroomNotFoundException;
import com.example.onlinechat.exception.business.ChatroomNotJoinedException;
import com.example.onlinechat.exception.business.NonOwnerRoomDeleteException;
import com.example.onlinechat.exception.business.UserNotFoundException;
import com.example.onlinechat.repository.ChatRepository;
import com.example.onlinechat.repository.ChatRoomRepository;
import com.example.onlinechat.repository.UserRepository;
import com.example.onlinechat.service.ChatRoomService;
import com.example.onlinechat.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ChatRoomServiceTest {
    @Autowired
    ChatRoomService chatRoomService;
    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;


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
    public void findByIdTest() {
        ChatRoomDTO chatRoomDTO  = chatRoomService.findById(chatRoomDTOList.get(0).getId());

        assertThrows(ChatroomNotFoundException.class, () -> {
            chatRoomService.findById("[unknown_chatroom]");
        });
    }

    @Test
    public void joinAndLeaveChatroomTest() {
        String rid = chatRoomDTOList.get(0).getId();

        // 3명 채팅방에 join.
        for (int i=0; i<3; i++)
            chatRoomService.joinChatRoom(rid, userDTOList.get(i).getId());

        List<UserDTO> userJoining = chatRoomService.findAllUserInRoom(rid);
        assertThat(userJoining.size(), is(3));

        // 2명 room 에 leave.
        for (int i=0; i<2; i++)
            chatRoomService.leaveChatRoom(rid, userDTOList.get(i).getId());

        userJoining = chatRoomService.findAllUserInRoom(rid);
        assertThat(userJoining.size(), is(1));

        // 없는 사용자가 join.
        // 근데 컨트롤러에서 joinChatRoom 호출할때는 인증 필터 지나서 사용자가 반드시 존재한다.
        // 그래서 사용자가 없는 상황에 대한 예외는 없고 ChatroomNotFoundException 으로 뜬다.
        assertThrows(ChatroomNotFoundException.class, () -> {
            chatRoomService.joinChatRoom(rid, "???");
        });

        // 없는 방에 join
        assertThrows(ChatroomNotFoundException.class, () -> {
            chatRoomService.joinChatRoom("???", userDTOList.get(0).getId());
        });

        // 이미 떠난 사용자가 leave
        assertThrows(ChatroomNotJoinedException.class, () -> {
            chatRoomService.leaveChatRoom(rid, userDTOList.get(0).getId());
        });
    }

    @Test
    public void deleteRoom() {
        ChatRoomDTO chatRoomDTO = chatRoomDTOList.get(0);
        UserDTO userDTO =  userDTOList.get(0);

        // 채팅방 join.
        chatRoomService.joinChatRoom(chatRoomDTO.getId(), userDTO.getId());

        // join 한 채팅방 확인.
        List<ChatRoomDTO> res = userService.getUserParticipating(userDTO.getId());
        assertThat(res.size(), is(1));
        assertThat(res.get(0).getId(), is(chatRoomDTO.getId()));

        // 올바른 owner 가 삭제를 시도했는지.
        assertThrows(NonOwnerRoomDeleteException.class, () -> {
                    chatRoomService.deleteChatRoom(chatRoomDTO.getId(), "[NONO_OWNER]");
        });

        // 채팅방 삭제.
        chatRoomService.deleteChatRoom(chatRoomDTO.getId(), "owner1");

        // 삭제 잘 되었는지 확인.
        assertThrows(ChatroomNotFoundException.class, () -> {
            chatRoomService.findById(chatRoomDTO.getId());
        });

        // 유저도 자동으로 나가졌는지 확인.
        res = userService.getUserParticipating(userDTO.getId());
        assertThat(res.size(), is(0));
    }


}
