package com.matheusdev.talknow.config;

import com.matheusdev.talknow.service.TalkNowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

// Classe que manipula mensagens WebSocket(usado para lidar com as mensagens enviadas pelos usuários)
@Component
public class ChatHandler implements WebSocketHandler {

    @Autowired
    private TalkNowService talkNowService;

    // Método chamado após estabelecer a conexão WebSocket
    // Obtém o nome do usuário e do destinatário a partir da sessão e chama o método connectUser do TalkNowService para conectar o usuário
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

    // Método chamado quando uma mensagem é enviada pelo usuário
    // Obtém o conteúdo da mensagem e o nome do usuário e do destinatário a partir da sessão e chama o método sendMessage do TalkNowService para enviar a mensagem
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

    // Método chamando quando ocorre um erro de transporte(Não está sendo usado)
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    // Método é chamado após fechar a conexão WebSocket
    // Obtém o nome do usuário a partir da sessão e chama o método disconnectUser do serviço TalkNowService para desconectar o usuário
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        if (session.getAttributes().containsKey("username")) {
            String username = session.getAttributes().get("username").toString();
            talkNowService.disconnectUser(session, username);
        } else {
            throw new Exception("O atributo 'username' não está presente na sessão");
        }
    }

    // Método é chamado para verificar se o manipulador de mensagens suporta mensagens parciais
    // Ele retorna false
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
