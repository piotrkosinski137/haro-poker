package app.domain.game;

import java.util.Collection;
import org.springframework.context.ApplicationEvent;

public class GamePlayersChanged extends ApplicationEvent {

    private final Collection<GamePlayerDto> gamePlayers;

    public GamePlayersChanged(final Object source, final Collection<GamePlayerDto> gamePlayers) {
        super(source);
        this.gamePlayers = gamePlayers;
    }

    public Collection<GamePlayerDto> getGamePlayers() {
        return gamePlayers;
    }
}
