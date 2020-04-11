package app.domain.event;

import app.domain.round.RoundPlayer;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;

public class RoundPlayersChanged extends ApplicationEvent {

    private Collection<RoundPlayer> roundPlayers;

    public RoundPlayersChanged(Object source, Collection<RoundPlayer> roundPlayers) {
        super(source);
        this.roundPlayers = roundPlayers;
    }

    public Collection<RoundPlayer> getRoundPlayers() {
        return roundPlayers;
    }
}
