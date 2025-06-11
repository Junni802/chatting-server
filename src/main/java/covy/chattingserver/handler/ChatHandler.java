package covy.chattingserver.handler;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
public class ChatHandler extends TextWebSocketHandler {

  private static List<WebSocketSession> list = new ArrayList<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    list.add(session);
    log.info(session + " 클라이언트 접속");
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();
    log.info("payload : " + payload);
    //페이로드란 전송되는 데이터를 의미한다.
    for(WebSocketSession sess: list) {
      sess.sendMessage(message);
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    log.info(session + " 클라이언트 접속 해제");
    list.remove(session);
  }
}
