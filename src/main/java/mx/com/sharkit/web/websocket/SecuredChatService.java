package mx.com.sharkit.web.websocket;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import mx.com.sharkit.service.ChatService;
import mx.com.sharkit.web.websocket.dto.MessageChatDTO;
import mx.com.sharkit.web.websocket.dto.OutputChatMessageDTO;

@Controller
public class SecuredChatService {

    public static final String SECURED_CHAT_HISTORY = "/secured/history";
    public static final String SECURED_CHAT = "/secured/chat";
    public static final String SECURED_CHAT_ROOM = "/secured/room";
    public static final String SECURED_CHAT_SPECIFIC_USER = "/secured/user/queue/specific-user";
    
	@Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
    private ChatService chatService;
	
    private static final Logger log = LoggerFactory.getLogger(SecuredChatService.class);

    @MessageMapping(SECURED_CHAT)
    @SendTo(SECURED_CHAT_HISTORY)
    public OutputChatMessageDTO sendAll(MessageChatDTO msg) throws Exception {
        OutputChatMessageDTO out = new OutputChatMessageDTO(msg.getFrom(), msg.getText(), new SimpleDateFormat("HH:mm").format(new Date()));
        return out;
    }

    /**
     * Example of sending message to specific user using 'convertAndSendToUser()' and '/queue'
     */
    @MessageMapping(SECURED_CHAT_ROOM)
    public void sendSpecific(@Payload MessageChatDTO msg, Principal user, @Header("simpSessionId") String sessionId) throws Exception {
    	log.info("Mensaje recibido: {}", msg);
    	log.info("Principal: {}", user);
    	chatService.saveMensajeChat(msg);
        OutputChatMessageDTO out = new OutputChatMessageDTO(msg.getFrom(), msg.getText(), new SimpleDateFormat("HH:mm").format(new Date()));
        simpMessagingTemplate.convertAndSendToUser(msg.getTo(), SECURED_CHAT_SPECIFIC_USER, out);
    }
    
}
