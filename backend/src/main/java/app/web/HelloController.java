package app.web;

import app.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  private final GameService gameService;

  public HelloController(GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping("/hello")
  public Message sayHello() {
    return new Message("Hello from backend!");
  }
}

class Message {

  String content;

  public Message(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
