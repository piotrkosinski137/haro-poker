package app.domain.game;

import app.domain.event.GamePlayersChanged;
import app.domain.player.GamePlayer;
import java.util.Collection;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
class GameEventPublisher {

    private final ApplicationEventPublisher publisher;

    public GameEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishGameEvent(Game game) {
        final GameDto gameDto = new GameDto(game.getBlinds(), game.getGameTimeStamp());
        publisher.publishEvent(new GameChanged(this, gameDto));
    }

    public void publishGamePlayerEvent(Collection<GamePlayer> players) {
        publisher.publishEvent(new GamePlayersChanged(this, players));
    }
}