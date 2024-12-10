package com.matheusdev.talknow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

// Classe de configuração do WebSocket que é usada para registrar o manipulador de mensagens e o interceptador
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    // Método é chamado para registrar o manipulador de mensagens e o interceptador.
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler(), "/ws").addInterceptors(new ChatInterceptor());
    }

    // Esse método é chamado para criar uma instância do manipulador de mensagens.
    @Bean
    public ChatHandler chatHandler() {
        return new ChatHandler();
    }
}
