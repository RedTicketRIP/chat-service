package com.example.onlinechat.repository;

import com.example.onlinechat.domain.ChatRoom;
import com.example.onlinechat.domain.User;
import com.example.onlinechat.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserMapper userMapper;

    public Optional<User> findById(String id) {
        User user = userMapper.findById(id);

        return Optional.ofNullable(user);
    }

    // 특정 방에 속한 특정 유저 정보 불러오기.
    public Optional<User> findUserChatRoomMapping(String rid, String uid) {
        User user = userMapper.findUserChatRoomMapping(rid, uid);

        return Optional.ofNullable(user);
    }

    // 특정 유저가 속한 모든 방 불러오기.
    public List<ChatRoom> findAllChatRoomUserMapping(String uid) {
        return userMapper.findAllChatRoomUserMapping(uid);
    }


    public void updateIsOnline(String uid, Boolean isOnline) {
        userMapper.updateIsOnline(uid, isOnline);
    }

    public void save(User user) {
        String id = user.getId();
        String password = user.getPassword();
        boolean isOnline = user.getIsOnline();

        userMapper.save(id, password,isOnline);
    }

    public void deleteAll() {
        userMapper.deleteAll();
    }
}
