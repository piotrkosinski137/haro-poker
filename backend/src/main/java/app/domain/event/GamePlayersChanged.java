package app.domain.event;

import app.domain.player.GamePlayer;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;

public class GamePlayersChanged extends ApplicationEvent {

    private final Collection<GamePlayer> gamePlayers;

    public GamePlayersChanged(final Object source, final Collection<GamePlayer> gamePlayers) {
        super(source);
        this.gamePlayers = gamePlayers;
    }

    public Collection<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }
}
