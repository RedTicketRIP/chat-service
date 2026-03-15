package com.example.onlinechat.repository.interf;

public interface ChannelRepository {
    public void addUserInChannel(String dest, String subId, String sessionId);
    public void removeUserInChannel(String subId, String sessionId);
    public boolean isExistUserChannelMatch(String subId, String dest, String sessionId);
    public void removeSessionId(String sessionId);
}
