package com.example.onlinechat.repository;

import com.example.onlinechat.repository.interf.ChannelRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Repository
@Log4j2
public class InMemoryChannelRepository implements ChannelRepository {
    // Map<SessionId, Map<sub-id, dest>>
    private Map<String, Map<String, String>> subscriptions = new ConcurrentHashMap<>();

    public void addUserInChannel(String dest, String subId, String sessionId) {
        Map<String, String> subList =
                subscriptions.computeIfAbsent(sessionId, k -> new ConcurrentHashMap<>());

        if (subList.putIfAbsent(subId, dest) != null) {
            log.info("[addUserInChannel] Channel and chatroom states are out of sync.");
            throw new IllegalStateException("Channel and chatroom states are out of sync.");
        }
    }

    public void removeUserInChannel(String subId, String sessionId) {
        Map<String, String> subList = subscriptions.get(sessionId);
        if (subList == null) {
            log.info("[removeUserInChannel] Channel and chatroom states are out of sync.");
            throw new IllegalStateException("Channel and chatroom states are out of sync.");
        }

        subList.remove(subId);

        if (subList.isEmpty()) {
            subscriptions.remove(subList);
        }
    }

    public void removeSessionId(String sessionId) {
        subscriptions.remove(sessionId);
    }

    public boolean isExistUserChannelMatch(String subId, String dest, String sessionId) {
        assertNotNull(subId);
        assertNotNull(dest);
        assertNotNull(sessionId);

        Map<String, String> subList = subscriptions.get(sessionId);
        if (subList == null) {
            return false;
        }

        String val = subList.get(subId);
        if (val == null)
            return false;

        return val.equals(dest);
    }
}
