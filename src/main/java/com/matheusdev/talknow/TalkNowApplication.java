package com.matheusdev.talknow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
public class TalkNowApplication {

    public static void main(String[] args) {
        SpringApplication.run(TalkNowApplication.class, args);
    }

}
