package app.domain.event;

import app.domain.round.RoundPlayer;
import java.util.Collection;
import org.springframework.context.ApplicationEvent;

public class RoundPlayersChanged extends ApplicationEvent {

    private final Collection<RoundPlayer> roundPlayers;

    public RoundPlayersChanged(final Object source, final Collection<RoundPlayer> roundPlayers) {
        super(source);
        this.roundPlayers = roundPlayers;
    }

    public Collection<RoundPlayer> getRoundPlayers() {
        return roundPlayers;
    }
}
