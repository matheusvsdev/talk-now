package com.matheusdev.talknow.config;

import com.matheusdev.talknow.service.TalkNowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Component
public class ChatHandler implements WebSocketHandler {

    @Autowired
    private TalkNowService talkNowService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (session.getAttributes().containsKey("username")) {
            String username = session.getAttributes().get("username").toString();
            talkNowService.connectUser(session);
        } else {
            throw new Exception("O atributo 'username' não está presente na sessão");
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String content = textMessage.getPayload().toString();
            if (session.getAttributes().containsKey("username")) {
                String username = session.getAttributes().get("username").toString();
                talkNowService.sendMessage(session, username, content);
            } else {
                throw new Exception("O atributo 'username' não está presente na sessão");
            }
        } else {
            throw new Exception("O conteúdo da mensagem é nulo");
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        talkNowService.disconnectUser(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
