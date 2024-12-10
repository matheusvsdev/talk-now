package com.matheusdev.talknow.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Classe é um serviço que é usado para gerenciar as mensagens e os usuários conectados.
@Service
public class TalkNowService {

    private final Map<String, WebSocketSession> connectedUsers = new HashMap<>();

    // Método é chamado para enviar uma mensagem
    // Obtém a sessão do destinatário a partir do mapa de usuários conectados e envia a mensagem para o destinatário.
    public void sendMessage(WebSocketSession session, String username, String receiver, String message) throws IOException {
        WebSocketSession receiverSession = connectedUsers.get(receiver);
        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.sendMessage(new TextMessage(username + ": " + message));
        }
    }

    // Método é chamado para conectar um usuário
    // Adiciona o usuário ao mapa de usuários conectados e adiciona o atributo "receiver" à sessão.
    public void connectUser(WebSocketSession session, String username, String receiver) throws IOException {
        connectedUsers.put(username, session);
        session.getAttributes().put("receiver", receiver);
    }

    // Método é chamado para desconectar um usuário
    // Remove o usuário do mapa de usuários conectados.
    public void disconnectUser(WebSocketSession session, String username) throws IOException {
        connectedUsers.remove(username);
    }
}
