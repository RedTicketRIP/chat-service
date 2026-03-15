package com.example.onlinechat.service;

import com.example.onlinechat.domain.ChatRoom;
import com.example.onlinechat.domain.User;
import com.example.onlinechat.dto.ChatRoomDTO;
import com.example.onlinechat.dto.UserDTO;
import com.example.onlinechat.dto.UserSummaryDTO;
import com.example.onlinechat.exception.business.*;
import com.example.onlinechat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ModelMapper modelMapper;


    public ChatRoomDTO findById(String rid) {
        ChatRoom chatRoom = chatRoomRepository.findById(rid).orElseThrow(
                () -> new ChatroomNotFoundException(rid)
        );

        List<User> users = chatRoomRepository.findAllUserChatRoomMapping(chatRoom.getId());

        ChatRoomDTO chatRoomDTO = modelMapper.map(chatRoom, ChatRoomDTO.class);
        for (User user : users) {
            UserSummaryDTO userSummaryDTO = UserSummaryDTO.builder()
                    .id(user.getId())
                    .build();

            chatRoomDTO.addJoinerList(userSummaryDTO);
        }

        return chatRoomDTO;
    }

    @Transactional
    public void joinChatRoom(String rid, String uid) {
        try {
            chatRoomRepository.addUserChatRoomMapping(rid, uid);

        } catch (DuplicateKeyException duplicateKeyException) { // 이미 채팅방에 참여중인 user.
            throw new ChatroomAlreadyJoinedException(rid, uid);
        } catch (DataIntegrityViolationException integrityViolationException) { // 존재하지 않는 채팅방.
            throw new ChatroomNotFoundException(rid, integrityViolationException);
        }
    }

    public List<UserDTO> findAllUserInRoom(String rid) {
        return chatRoomRepository.findAllUserChatRoomMapping(rid).stream().map(user -> {
            return modelMapper.map(user, UserDTO.class);
        }).toList();
    }


    @Transactional
    public void leaveChatRoom(String rid, String uid) {
        ChatRoom chatRoom = chatRoomRepository.findByIdForUpdate(rid).orElseThrow(
                () -> new ChatroomNotFoundException(rid)
        );

        if (chatRoom.getOwnerId().equals(uid)) {
            throw new OwnerLeaveException(rid, uid);
        }

        int row = chatRoomRepository.removeUserChatRoomMapping(rid, uid);
        if (row == 0) {
            throw new ChatroomNotJoinedException(rid , uid);
        }
    }

    public Boolean isUserJoinedInChatroom(String rid, String uid) {
        return chatRoomRepository.isExistUserChatroomMatch(rid, uid);
    }

    public List<ChatRoomDTO> findAllChatRoom() {
        return chatRoomRepository.findAll().stream().map(chatroom -> {
            return modelMapper.map(chatroom, ChatRoomDTO.class);
        }).toList();
    }



    public void createChatRoom(ChatRoomDTO chatRoomDTO, String roomOwnerId) {
        ChatRoom chatRoom = modelMapper.map(chatRoomDTO, ChatRoom.class);
        chatRoom.setOwnerId(roomOwnerId);

        try {
            chatRoomRepository.save(chatRoom);

        } catch (DuplicateKeyException duplicateKeyException) {
            throw new ChatroomAlreadyExistException(chatRoom.getId());
        }
    }


    @Transactional
    public void deleteChatRoom(String rid, String ownerId) {
        ChatRoom chatRoom = chatRoomRepository.findByIdForUpdate(rid).orElseThrow(
                () -> new ChatroomNotFoundException(rid)
        );

        if (chatRoom.getOwnerId().equals(ownerId)) {
            chatRoomRepository.deleteById(rid);
        } else {
            throw new NonOwnerRoomDeleteException(rid, ownerId);
        }
    }
}
