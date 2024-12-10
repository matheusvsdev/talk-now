package com.matheusdev.talknow.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TalkNowService {

    private final Map<String, WebSocketSession> connectedUsers = new HashMap<>();

    public void sendMessage(WebSocketSession session, String username, String receiver, String message) throws IOException {
        WebSocketSession receiverSession = connectedUsers.get(receiver);
        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.sendMessage(new TextMessage(username + ": " + message));
        }
    }

    public void connectUser(WebSocketSession session, String username, String receiver) throws IOException {
        connectedUsers.put(username, session);
        session.getAttributes().put("receiver", receiver);
    }

    public void disconnectUser(WebSocketSession session, String username) throws IOException {
        connectedUsers.remove(username);
    }
}
