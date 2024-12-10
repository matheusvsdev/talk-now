package com.matheusdev.talknow.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Service
public class TalkNowService {

    private final Set<WebSocketSession> connectedUsers = new HashSet<>();

    public void sendMessage(WebSocketSession session, String message) {
        for (WebSocketSession connectedSession : connectedUsers) {
            if (connectedSession.equals(session)) {
                connectedSession.sendMessage(new TextMessage(message));
            }
        }
    }

    public void connectUser(WebSocketSession session) {
        connectedUsers.add(session);
    }

    public void disconnectUser(WebSocketSession session) {
        connectedUsers.remove(session);
    }
}
