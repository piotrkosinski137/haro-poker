package app.domain.game;

import java.util.Collection;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
class GameEventPublisher {

    private final ApplicationEventPublisher publisher;

    public GameEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishGameEvent(GameDto gameDto) {
        publisher.publishEvent(new GameChanged(this, gameDto));
    }

    public void publishGamePlayerEvent(Collection<GamePlayerDto> players) {
        publisher.publishEvent(new GamePlayersChanged(this, players));
    }
}
