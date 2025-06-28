package org.sorter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SortWebSocketHandler webSocketHandler;

    // Inject the singleton bean via constructor
    public WebSocketConfig(SortWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/sorter")
                .setAllowedOrigins("http://127.0.0.1:8081");  // Add allowed origins for WebSocket
    }
}