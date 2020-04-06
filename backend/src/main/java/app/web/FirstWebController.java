package app.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class FirstWebController {

    @MessageMapping("/poker")
    public String greeting(@Payload String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Hello!" + message;
    }
}