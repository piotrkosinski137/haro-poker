import { StompService, StompConfig, StompState } from "@stomp/ng2-stompjs";
import { Message } from "@stomp/stompjs";
import { Observable, BehaviorSubject } from "rxjs";

export class MessagingService {
  private messages: Observable<Message>;
  private stompService: StompService;

  constructor(socketUrl, responsePath: string) {
    let stompConfig: StompConfig = {
      url: socketUrl,
      headers: {
        login: "",
        passcode: ""
      },
      heartbeat_in: 0,
      heartbeat_out: 20000,
      reconnect_delay: 5000,
      debug: true
    };
    this.stompService = new StompService(stompConfig);
    this.messages = this.stompService.subscribe(responsePath);
  }

  public stream(): Observable<Message> {
    return this.messages;
  }

  public send(url: string, message: any) {
    return this.stompService.publish(url, message);
  }

  public state(): BehaviorSubject<StompState> {
    return this.stompService.state;
  }
}
