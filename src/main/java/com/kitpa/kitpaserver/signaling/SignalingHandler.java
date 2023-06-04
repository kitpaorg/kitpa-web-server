package com.kitpa.kitpaserver.signaling;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SignalingHandler extends TextWebSocketHandler {
    List<WebSocketSession> sessions = new LinkedList<WebSocketSession>();
    ConcurrentHashMap<String, WebSocketSession> sessionMap = new ConcurrentHashMap<String, WebSocketSession>();
    final ObjectMapper map1 = new ObjectMapper();

    static final String joinRoom = "join_room";
    static final String offer = "offer";
    static final String answer = "answer";
    static final String candidate = "candidate";
    static final String disconnect = "disconnect";

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        final String msg1 = message.getPayload();
        log.debug("Receive message from client:{}", msg1);
        session.getId();


    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        sessions.add(session);
        super.afterConnectionEstablished(session);
    }

}
