package com.example.onlinechat.repository;

import com.example.onlinechat.domain.ChatRoom;
import com.example.onlinechat.domain.User;
import com.example.onlinechat.repository.mapper.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository{
    private final ChatRoomMapper chatRoomMapper;

    @Transactional
    public Optional<ChatRoom> findById(String rid) {
        ChatRoom chatRoom =  chatRoomMapper.findByIdForUpdate(rid);

        return Optional.ofNullable(chatRoom);
    }


    public Optional<ChatRoom> findByIdForUpdate(String rid) {
        ChatRoom chatRoom =  chatRoomMapper.findByIdForUpdate(rid);

        return Optional.ofNullable(chatRoom);
    }


    public List<ChatRoom> findAll() {
        return chatRoomMapper.findAll();
    }

    public void save(ChatRoom chatRoom) {
        String rid = chatRoom.getId();
        String ownerId = chatRoom.getOwnerId();

        chatRoomMapper.save(rid, ownerId);
    }

    // user-chatroom 중간테이블에서 특정 chatroom 에 맞는 모든 유저 반환.
    public List<User> findAllUserChatRoomMapping(String rid) {
        return chatRoomMapper.findAllUserChatRoomMapping(rid);
    }

    public void deleteById(String rid) {
         chatRoomMapper.deleteById(rid);
    }

    public void addUserChatRoomMapping(String rid, String uid) {
        chatRoomMapper.addUserChatRoomMapping(rid, uid);
    }



    public int removeUserChatRoomMapping(String rid, String uid) {
        return chatRoomMapper.removeUserChatRoomMapping(rid, uid);
    }

    public Boolean isExistUserChatroomMatch(String rid, String uid) {
        return chatRoomMapper.isExistUserChatroomMatch(rid, uid);
    }

    public void deleteAll() {
        chatRoomMapper.deleteAll();
    }

}












