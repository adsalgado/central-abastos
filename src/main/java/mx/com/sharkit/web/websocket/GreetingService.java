package mx.com.sharkit.web.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import mx.com.sharkit.web.websocket.dto.GreetingDTO;
import mx.com.sharkit.web.websocket.dto.HelloMessageDTO;

@Controller
public class GreetingService {

	@MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GreetingDTO greeting(HelloMessageDTO message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new GreetingDTO("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
	
}
