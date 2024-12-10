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
        if (session.getAttributes().containsKey("username") && session.getAttributes().containsKey("receiver")) {
            String username = session.getAttributes().get("username").toString();
            String receiver = session.getAttributes().get("receiver").toString();
            talkNowService.connectUser(session, username, receiver);
        } else {
            throw new Exception("O atributo 'username' não está presente na sessão");
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String content = textMessage.getPayload().toString();
            if (content != null) {
                if (session.getAttributes().containsKey("username") && session.getAttributes().containsKey("receiver")) {
                    String username = session.getAttributes().get("username").toString();
                    String receiver = session.getAttributes().get("receiver").toString();
                    talkNowService.sendMessage(session, username, receiver, content);
                } else {
                    throw new Exception("O atributo 'username' ou 'receiver' não está presente na sessão");
                }
            }  else {
                throw new Exception("O conteúdo da mensagem é nulo");
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        if (session.getAttributes().containsKey("username")) {
            String username = session.getAttributes().get("username").toString();
            talkNowService.disconnectUser(session, username);
        } else {
            throw new Exception("O atributo 'username' não está presente na sessão");
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
