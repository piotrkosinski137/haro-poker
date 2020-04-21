package app.domain.round;

import java.util.Collection;
import org.springframework.context.ApplicationEvent;

public class RoundPlayersChanged extends ApplicationEvent {

    private final Collection<RoundPlayer> roundPlayers;
    private final boolean isWithCards;

    public RoundPlayersChanged(final Object source, final Collection<RoundPlayer> roundPlayers) {
        super(source);
        this.isWithCards = false;
        this.roundPlayers = roundPlayers;
    }

    public RoundPlayersChanged(final Object source, final Collection<RoundPlayer> roundPlayers, boolean isWithCards) {
        super(source);
        this.isWithCards = isWithCards;
        this.roundPlayers = roundPlayers;
    }

    public Collection<RoundPlayer> getRoundPlayers() {
        return roundPlayers;
    }

    public boolean isWithCards() {
        return isWithCards;
    }
}
