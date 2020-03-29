package app.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/hello")
  public Message sayHello() {
    return new Message("Hello from backend!");
  }

  @GetMapping("/api/hello")
  public Message sayHello2() {
    return new Message("Hello from backend2!");
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
