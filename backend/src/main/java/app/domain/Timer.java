package app.domain;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class Timer {

    private final SimpMessagingTemplate template;
    private final Thread thread;
    private final Time time;
    private Instant startGame;

    public Timer(SimpMessagingTemplate template) {
        this.template = template;
        thread = new Thread(this::runTimer);
        time = new Time();
    }

    public void start() {
        startGame = Instant.now();
        thread.start();
    }

    public void runTimer() {
        while (true) {
            sleepSecond();
            template.convertAndSend("/topic/timer", time.update(Duration.between(startGame, Instant.now()).toSeconds()));
        }
    }

    private void sleepSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        thread.stop();
    }
}
