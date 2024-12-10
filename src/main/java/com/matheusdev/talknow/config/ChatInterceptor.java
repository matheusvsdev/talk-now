package com.matheusdev.talknow.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

// Classe interceptador de requisições HTTP(Usado para obter o nome do usuário e do destinatário a partir da URL do WebSocket
@Component
public class ChatInterceptor implements HandshakeInterceptor {

    // Método é chamado antes de estabelecer a conexão WebSocket
    // Obtém o nome do usuário e do destinatário a partir da URL do WebSocket e adiciona os atributos "username" e "receiver" à sessão
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String username = request.getURI().getQuery().split("&")[0].split("=")[1];
        String receiver = request.getURI().getQuery().split("&")[1].split("=")[1];
        attributes.put("username", username);
        attributes.put("receiver", receiver);
        return true;
    }

    // Método é chamado após estabelecer a conexão WebSocket(Não está sendo usado)
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
