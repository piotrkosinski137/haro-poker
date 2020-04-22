package app.domain.round;

import java.util.Deque;
import org.springframework.context.ApplicationEvent;

public class RoundPlayersChanged extends ApplicationEvent {

    private final Deque<RoundPlayerDto> roundPlayers;

    public RoundPlayersChanged(final Object source, final Deque<RoundPlayerDto> roundPlayers) {
        super(source);
        this.roundPlayers = roundPlayers;
    }

    public Deque<RoundPlayerDto> getRoundPlayers() {
        return roundPlayers;
    }
}
